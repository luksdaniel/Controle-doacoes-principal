package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.coletaDoacao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.BusinessException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceCreateErrorException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.item.ItemManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.usuario.Usuario;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.usuario.UsuarioManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.itemColetaDoacao.ItemColetaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.doador.DoadorManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.itemColetaDoacao.ItemColetaManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.coletaDoacao.ColetaDoacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
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
    public List<ColetaDoacao> findByIdUsuarioRegistro(Long id) {
        List<ColetaDoacao> coletaList = coletaDoacaoService.findByIdUsuarioRegistro(id);

        if (coletaList.isEmpty()){
            throw new ResourceNotFoundException("Coletas não encontradas");
        }else {
            return coletaList;
        }
    }

    @Override
    public Optional<ColetaDoacao> findById(Long id) {
        Optional<ColetaDoacao> coletaDoacao = coletaDoacaoService.findById(id);
        if(coletaDoacao.isEmpty()){
            throw new ResourceNotFoundException("Coleta não encontrada");
        }else{
            return coletaDoacao;
        }
    }

    @Override
    public ColetaDoacao saveColetaDoacao(ColetaDoacao coletaDoacao) {
        setaAtributosIniciais(coletaDoacao);

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
        return null;
    }

    @Override
    public ColetaDoacao cancelaColetaDoacao(ColetaDoacao coletaDoacao) {

        if (coletaDoacao.getMotivoCancelamento() == null || coletaDoacao.getMotivoCancelamento().isBlank())
            throw new BusinessException("Deve ser informado o motivo de cancelamento");

        Optional<ColetaDoacao> coletaDoacaoInterna = coletaDoacaoService.findById(coletaDoacao.getId());
        String motivoCancelamento = coletaDoacao.getMotivoCancelamento();

        if(coletaDoacaoInterna.isEmpty())
            throw new ResourceNotFoundException("Coleta de doação não encontrada");

        if (coletaDoacaoInterna.get().isEstaCancelada())
            throw new BusinessException("A coleta de doação já está cancelada");

        movimentaEstoqueSeJaEfetivada(coletaDoacaoInterna.get());

        coletaDoacaoInterna.get().setMotivoCancelamento(motivoCancelamento);
        coletaDoacaoInterna.get().setEstaCancelada(true);

        return coletaDoacaoService.updateColetaDoacao(coletaDoacaoInterna.get());
    }

    @Override
    public ColetaDoacao efetivaColetaDoacao(Long id, Long usuarioId) {
        Optional<ColetaDoacao> coletaDoacao = coletaDoacaoService.findById(id);
        Optional<Usuario> usuarioEfetivador = usuarioManager.findById(usuarioId);

        if(coletaDoacao.isEmpty())
            throw new ResourceNotFoundException("Coleta de doação não encontrada");
        else if(usuarioEfetivador.isEmpty())
            throw new ResourceNotFoundException("Usuario não encontrado");

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

        coletaDoacao.setEstaCancelada(false);
        coletaDoacao.setUsuarioRegistro(usuarioManager.findById(coletaDoacao.getUsuarioRegistro().getId()).get());
        coletaDoacao.setDoador(doadorManager.findById(coletaDoacao.getDoador().getId()).get());

        System.out.println(coletaDoacao.getItensColeta());

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
        itemColetaManager.saveAllItensColeta(coletaDoacao.getItensColeta());
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
