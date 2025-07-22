package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.coletaDoacao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.Enumerators.Role;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.BusinessException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceCreateErrorException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.service.item.ItemManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.entity.usuario.Usuario;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.entity.usuario.UsuarioManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.itemColetaDoacao.ItemColetaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.doador.DoadorManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.itemColetaDoacao.ItemColetaManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.coletaDoacao.ColetaDoacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

@Component
@Transactional
public class ColetaDoacaoManagerImp implements ColetaDoacaoManager{

    @Autowired
    UsuarioManager usuarioManager;

    @Autowired
    ColetaDoacaoService coletaDoacaoService;

    @Autowired
    DoadorManager doadorManager;

    @Autowired
    ItemManager itemManager;

    @Autowired
    ItemColetaManager itemColetaManager;

    @Override
    public List<ColetaDoacao> findAllColetaDoacao() {
        List<ColetaDoacao> coletaList = coletaDoacaoService.findAllColetaDoacao();
        if (coletaList.isEmpty()){
            throw new ResourceNotFoundException("Coletas não encontradas");
        }else {
            return coletaList;
        }
    }

    @Override
    public List<ColetaDoacao> findByIdDoador(Long id) {
        List<ColetaDoacao> coletaList = coletaDoacaoService.findByIdDoador(id);

        if (coletaList.isEmpty()){
            throw new ResourceNotFoundException("Coletas não encontradas");
        }else {
            return coletaList;
        }
    }

    @Override
    public List<ColetaDoacao> findByIdUsuarioRegistro(String username) {
        Optional<Usuario> usuario = usuarioManager.findByUserName(username);
        List<ColetaDoacao> coletaList = coletaDoacaoService.findByIdUsuarioRegistro(usuario.get().getId());

        if (coletaList.isEmpty()){
            throw new ResourceNotFoundException("Coletas não encontradas");
        }else {
            return coletaList;
        }
    }

    @Override
    public Optional<ColetaDoacao> findById(Long id) {
        Optional<ColetaDoacao> coletaDoacao = coletaDoacaoService.findById(id);
        if(!coletaDoacao.isPresent()){
            throw new ResourceNotFoundException("Coleta não encontrada");
        }else{
            return coletaDoacao;
        }
    }

    @Override
    public ColetaDoacao saveColetaDoacao(ColetaDoacao coletaDoacao) {
        setaAtributosIniciais(coletaDoacao);
        coletaDoacao.setEstaCancelada(false);

        ColetaDoacao coletaDoacaoInterna = coletaDoacaoService.saveColetaDoacao(coletaDoacao);
        if(coletaDoacaoInterna == null){
            throw new ResourceCreateErrorException("Não foi possível gravar a coleta de doação!");
        }else {
            gravaItensColeta(coletaDoacao);
            return coletaDoacaoInterna;
        }
    }

    @Override
    public ColetaDoacao updateColetaDoacao(ColetaDoacao coletaDoacao) {
        Optional<ColetaDoacao> coletaDoacaoInterna = findById(coletaDoacao.getId());

        if (coletaDoacaoInterna.get().isEstaCancelada())
            throw new BusinessException("A coleta de doação está cancelada");
        if (coletaDoacaoInterna.get().isEstaEfetivada())
            throw new BusinessException("A coleta de doação está efetivada");

        setaAtributosIniciais(coletaDoacao);
        ColetaDoacao coletaDoacao1 = coletaDoacaoService.updateColetaDoacao(coletaDoacao);
        atualizaItensColeta(coletaDoacao);
        return coletaDoacao1;
    }

    @Override
    public ColetaDoacao cancelaColetaDoacao(ColetaDoacao coletaDoacao) {

        if (coletaDoacao.getMotivoCancelamento() == null || coletaDoacao.getMotivoCancelamento().isEmpty())
            throw new BusinessException("Deve ser informado o motivo de cancelamento");

        Optional<ColetaDoacao> coletaDoacaoInterna = findById(coletaDoacao.getId());
        Optional<Usuario> usuarioCancelamento = usuarioManager.findById(coletaDoacao.getUsuarioCancelamento().getId());
        String motivoCancelamento = coletaDoacao.getMotivoCancelamento();

        if (coletaDoacaoInterna.get().isEstaCancelada())
            throw new BusinessException("A coleta de doação já está cancelada");

        if(usuarioCancelamento.get().haveRole(Role.ROLE_DOADOR)
                && !usuarioCancelamento.get().getDoador().equals(coletaDoacaoInterna.get().getDoador())){
            throw new BusinessException("Doadores só podem cancelar suas próprias coletas!");
        }

        if(usuarioCancelamento.get().haveRole(Role.ROLE_DOADOR)
                && coletaDoacaoInterna.get().isEstaEfetivada()){
            throw new BusinessException("Coleta já efetivada, não pode ser cancelada pelo doador!");
        }

        movimentaEstoqueSeJaEfetivada(coletaDoacaoInterna.get());

        coletaDoacaoInterna.get().setMotivoCancelamento(motivoCancelamento);
        coletaDoacaoInterna.get().setEstaCancelada(true);
        coletaDoacaoInterna.get().setUsuarioCancelamento(usuarioCancelamento.get());

        return coletaDoacaoService.updateColetaDoacao(coletaDoacaoInterna.get());
    }

    @Override
    public ColetaDoacao efetivaColetaDoacao(Long id, Long usuarioId) {
        Optional<ColetaDoacao> coletaDoacao = findById(id);
        Optional<Usuario> usuarioEfetivador = usuarioManager.findById(usuarioId);

        if (coletaDoacao.get().isEstaCancelada())
            throw new BusinessException("A coleta de doação está cancelada, não sendo possível efetivá-la");
        else if (coletaDoacao.get().isEstaEfetivada())
            throw new BusinessException("A coleta de doação já foi efetivada");

        List<ItemColetaDoacao> itensColeta = coletaDoacao.get().getItensColeta();
        for (ItemColetaDoacao itemAtual: itensColeta) {
            itemManager.validaAndMovimentaEstoque(itemAtual.getItem(), itemAtual.getQuantidade());
        }
        coletaDoacao.get().setEstaEfetivada(true);
        coletaDoacao.get().setUsuarioEfetivacao(usuarioManager.findById(usuarioId).get());
        coletaDoacao.get().setDataEfetivacao(LocalDate.now());
        
        return coletaDoacaoService.updateColetaDoacao(coletaDoacao.get());
    }

    private void setaAtributosIniciais(ColetaDoacao coletaDoacao){

        coletaDoacao.setDataDoacao(LocalDate.now());
        coletaDoacao.setUsuarioRegistro(usuarioManager.findById(coletaDoacao.getUsuarioRegistro().getId()).get());
        coletaDoacao.setDoador(doadorManager.findById(coletaDoacao.getDoador().getId()).get());

        List<ItemColetaDoacao> itensDoacao = coletaDoacao.getItensColeta();
        List<ItemColetaDoacao> itensCompletos = new ArrayList<>();

        for (ItemColetaDoacao itemColetaAtual: itensDoacao) {
            /*if (itemColetaAtual.getId() != 0){
                itemColetaAtual = itemColetaManager.findByIdd(itemColetaAtual.getId());
            }*/
            itemColetaAtual.setItem(itemManager.findById(itemColetaAtual.getItem().getId()).get());
            itemColetaAtual.setColetaDoacao(coletaDoacao);
            itensCompletos.add(itemColetaAtual);
        }

        coletaDoacao.setItensColeta(itensCompletos);
    }

    private void gravaItensColeta(ColetaDoacao coletaDoacao){
        itemColetaManager.saveAllItensColeta(coletaDoacao.getItensColeta());
    }

    private void atualizaItensColeta(ColetaDoacao coletaDoacao){
        itemColetaManager.updateAllItensColeta(coletaDoacao.getItensColeta(), coletaDoacao.getId());
    }

    private void movimentaEstoqueSeJaEfetivada(ColetaDoacao coletaDoacao){
        if (coletaDoacao.isEstaEfetivada()){
            List<ItemColetaDoacao> itensColeta = coletaDoacao.getItensColeta();
            for (ItemColetaDoacao itemAtual: itensColeta) {
                itemManager.validaAndMovimentaEstoque(itemAtual.getItem(), -itemAtual.getQuantidade());
            }
        }
    }
}
