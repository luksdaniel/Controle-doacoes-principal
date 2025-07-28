package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.service.coletaDoacao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.Enumerators.Role;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.BusinessException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceCreateErrorException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.service.item.ItemManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.ColetaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.repository.ColetaDoacaoRepository;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.entity.Usuario;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.service.usuario.UsuarioServiceBase;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.ItemColetaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.service.doador.DoadorServiceBase;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.service.itemColetaDoacao.ItemColetaServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

@Component
@Transactional
public class ColetaDoacaoService implements ColetaDoacaoServiceBase {

    UsuarioServiceBase usuarioServiceBase;
    ColetaDoacaoRepository coletaDoacaoRepository;
    DoadorServiceBase doadorServiceBase;
    ItemManager itemManager;
    ItemColetaServiceBase itemColetaServiceBase;

    @Autowired
    public ColetaDoacaoService(UsuarioServiceBase usuarioServiceBase, ColetaDoacaoRepository coletaDoacaoRepository, DoadorServiceBase doadorServiceBase, ItemManager itemManager, ItemColetaServiceBase itemColetaServiceBase) {
        this.usuarioServiceBase = usuarioServiceBase;
        this.coletaDoacaoRepository = coletaDoacaoRepository;
        this.doadorServiceBase = doadorServiceBase;
        this.itemManager = itemManager;
        this.itemColetaServiceBase = itemColetaServiceBase;
    }

    @Override
    public List<ColetaDoacao> findAllColetaDoacao() {
        List<ColetaDoacao> coletaList = coletaDoacaoRepository.findAll();
        if (coletaList.isEmpty()){
            throw new ResourceNotFoundException("Coletas não encontradas");
        }else {
            return coletaList;
        }
    }

    @Override
    public List<ColetaDoacao> findByIdDoador(Long id) {
        List<ColetaDoacao> coletaList = coletaDoacaoRepository.findColetaDoacaoByDoadorId(id);

        if (coletaList.isEmpty()){
            throw new ResourceNotFoundException("Coletas não encontradas");
        }else {
            return coletaList;
        }
    }

    @Override
    public List<ColetaDoacao> findByIdUsuarioRegistro(String username) {
        Optional<Usuario> usuario = usuarioServiceBase.findByUserName(username);
        List<ColetaDoacao> coletaList = coletaDoacaoRepository.findColetaDoacaoByUsuarioRegistroId(usuario.get().getId());

        if (coletaList.isEmpty()){
            throw new ResourceNotFoundException("Coletas não encontradas");
        }else {
            return coletaList;
        }
    }

    @Override
    public Optional<ColetaDoacao> findById(Long id) {
        Optional<ColetaDoacao> coletaDoacao = coletaDoacaoRepository.findById(id);
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

        ColetaDoacao coletaDoacaoInterna = coletaDoacaoRepository.save(coletaDoacao);
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
        ColetaDoacao coletaDoacao1 = coletaDoacaoRepository.save(coletaDoacao);
        atualizaItensColeta(coletaDoacao);
        return coletaDoacao1;
    }

    @Override
    public ColetaDoacao cancelaColetaDoacao(ColetaDoacao coletaDoacao) {

        if (coletaDoacao.getMotivoCancelamento() == null || coletaDoacao.getMotivoCancelamento().isEmpty())
            throw new BusinessException("Deve ser informado o motivo de cancelamento");

        Optional<ColetaDoacao> coletaDoacaoInterna = findById(coletaDoacao.getId());
        Optional<Usuario> usuarioCancelamento = usuarioServiceBase.findById(coletaDoacao.getUsuarioCancelamento().getId());
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

        return coletaDoacaoRepository.save(coletaDoacaoInterna.get());
    }

    @Override
    public ColetaDoacao efetivaColetaDoacao(Long id, Long usuarioId) {
        Optional<ColetaDoacao> coletaDoacao = findById(id);
        Optional<Usuario> usuarioEfetivador = usuarioServiceBase.findById(usuarioId);

        if (coletaDoacao.get().isEstaCancelada())
            throw new BusinessException("A coleta de doação está cancelada, não sendo possível efetivá-la");
        else if (coletaDoacao.get().isEstaEfetivada())
            throw new BusinessException("A coleta de doação já foi efetivada");

        List<ItemColetaDoacao> itensColeta = coletaDoacao.get().getItensColeta();
        for (ItemColetaDoacao itemAtual: itensColeta) {
            itemManager.validaAndMovimentaEstoque(itemAtual.getItem(), itemAtual.getQuantidade());
        }
        coletaDoacao.get().setEstaEfetivada(true);
        coletaDoacao.get().setUsuarioEfetivacao(usuarioServiceBase.findById(usuarioId).get());
        coletaDoacao.get().setDataEfetivacao(LocalDate.now());
        
        return coletaDoacaoRepository.save(coletaDoacao.get());
    }

    private void setaAtributosIniciais(ColetaDoacao coletaDoacao){

        coletaDoacao.setDataDoacao(LocalDate.now());
        coletaDoacao.setUsuarioRegistro(usuarioServiceBase.findById(coletaDoacao.getUsuarioRegistro().getId()).get());
        coletaDoacao.setDoador(doadorServiceBase.findById(coletaDoacao.getDoador().getId()).get());

        List<ItemColetaDoacao> itensDoacao = coletaDoacao.getItensColeta();
        List<ItemColetaDoacao> itensCompletos = new ArrayList<>();

        for (ItemColetaDoacao itemColetaAtual: itensDoacao) {
            itemColetaAtual.setItem(itemManager.findById(itemColetaAtual.getItem().getId()).get());
            itemColetaAtual.setColetaDoacao(coletaDoacao);
            itensCompletos.add(itemColetaAtual);
        }

        coletaDoacao.setItensColeta(itensCompletos);
    }

    private void gravaItensColeta(ColetaDoacao coletaDoacao){
        itemColetaServiceBase.saveAllItensColeta(coletaDoacao.getItensColeta());
    }

    private void atualizaItensColeta(ColetaDoacao coletaDoacao){
        itemColetaServiceBase.updateAllItensColeta(coletaDoacao.getItensColeta(), coletaDoacao.getId());
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
