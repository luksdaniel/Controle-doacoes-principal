package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.entregaDoacao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.BusinessException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceCreateErrorException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.item.ItemManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.coletaDoacao.ColetaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.itemColetaDoacao.ItemColetaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.itemEntregaDoacao.ItemEntregaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.beneficiario.BeneficiarioManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.itemEntregaDoacao.ItemEntregaManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.entregaDoacao.EntregaDoacaoService;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.itemEntregaDoacao.ItemEntregaService;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.entity.usuario.Usuario;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.entity.usuario.UsuarioManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class EntregaDoacaoManagerImp implements EntregaDoacaoManager{


    @Autowired
    EntregaDoacaoService entregaDoacaoService;

    @Autowired
    UsuarioManager usuarioManager;

    @Autowired
    BeneficiarioManager beneficiarioManager;

    @Autowired
    ItemManager itemManager;

    @Autowired
    ItemEntregaManager itemEntregaManager;


    @Override
    public List<EntregaDoacao> findAllEntregaDoacao() {
        List<EntregaDoacao> entregaList = entregaDoacaoService.findAllEntregaDoacao();
        if (entregaList.isEmpty()){
            throw new ResourceNotFoundException("Entregas não encontradas");
        }else {
            return entregaList;
        }
    }

    @Override
    public List<EntregaDoacao> findByIdBeneficiario(Long id) {
        List<EntregaDoacao> entregaList = entregaDoacaoService.findByBeneficiarioId(id);

        if (entregaList.isEmpty()){
            throw new ResourceNotFoundException("Entregas não encontradas");
        }else {
            return entregaList;
        }
    }

    @Override
    public List<EntregaDoacao> findByIdUsuarioRegistro(Long id) {
        List<EntregaDoacao> entregaList = entregaDoacaoService.findByUsuarioRegistroId(id);

        if (entregaList.isEmpty()){
            throw new ResourceNotFoundException("Entregas não encontradas");
        }else {
            return entregaList;
        }
    }

    @Override
    public Optional<EntregaDoacao> findById(Long id) {
        Optional<EntregaDoacao> entregaList = entregaDoacaoService.findById(id);
        if(entregaList.isEmpty()){
            throw new ResourceNotFoundException("Entrega não encontrada");
        }else{
            return entregaList;
        }
    }

    @Override
    public EntregaDoacao saveEntregaDoacao(EntregaDoacao entregaDoacao) {
        setaAtributosIniciais(entregaDoacao);

        List<ItemEntregaDoacao> itensEntrega = entregaDoacao.getItensEntrega();
        for (ItemEntregaDoacao itemAtual: itensEntrega) {
            itemManager.validaAndMovimentaEstoque(itemAtual.getItem(), itemAtual.getQuantidade());
        }

        EntregaDoacao entregaInterna = entregaDoacaoService.saveEntregaDoacao(entregaDoacao);
        if(entregaInterna == null){
            throw new ResourceCreateErrorException("Não foi possível gravar a entrega de doação!");
        }else {
            gravaItensEntrega(entregaDoacao);
            return entregaInterna;
        }
    }

    @Override
    public EntregaDoacao updateEntregaDoacao(EntregaDoacao entregaDoacao) {
        Optional<EntregaDoacao> entregaDoacaoInterna = findById(entregaDoacao.getId());

        if (entregaDoacaoInterna.get().isEstaCancelada())
            throw new BusinessException("A coleta de doação está cancelada");

        setaAtributosIniciais(entregaDoacao);
        EntregaDoacao entregaDoacao1 = entregaDoacaoService.updateEntregaDoacao(entregaDoacao);
        atualizaItensEntrega(entregaDoacao);
        return entregaDoacao1;
    }

    @Override
    public EntregaDoacao cancelaEntregaDoacao(EntregaDoacao entregaDoacao) {

        if (entregaDoacao.getMotivoCancelamento() == null || entregaDoacao.getMotivoCancelamento().isBlank())
            throw new BusinessException("Deve ser informado o motivo de cancelamento");

        Optional<EntregaDoacao> entregaDoacaoInterna = findById(entregaDoacao.getId());
        Optional<Usuario> usuarioCancelamento = usuarioManager.findById(entregaDoacao.getUsuarioCancelamento().getId());
        String motivoCancelamento = entregaDoacao.getMotivoCancelamento();

        if (entregaDoacaoInterna.get().isEstaCancelada())
            throw new BusinessException("A entrega de doação já está cancelada");

        movimentaEstoqueCancelamento(entregaDoacaoInterna.get());

        entregaDoacaoInterna.get().setMotivoCancelamento(motivoCancelamento);
        entregaDoacaoInterna.get().setEstaCancelada(true);
        entregaDoacaoInterna.get().setUsuarioCancelamento(usuarioCancelamento.get());

        return entregaDoacaoService.updateEntregaDoacao(entregaDoacaoInterna.get());
    }

    private void setaAtributosIniciais(EntregaDoacao entregaDoacao){

        entregaDoacao.setEstaCancelada(false);
        entregaDoacao.setDataEntrega(LocalDate.now());
        entregaDoacao.setUsuarioRegistro(usuarioManager.findById(entregaDoacao.getUsuarioRegistro().getId()).get());
        entregaDoacao.setBeneficiario(beneficiarioManager.findById(entregaDoacao.getBeneficiario().getId()).get());

        List<ItemEntregaDoacao> itensEntrega = entregaDoacao.getItensEntrega();
        List<ItemEntregaDoacao> itensCompletos = new ArrayList<>();

        for (ItemEntregaDoacao itemEntregaAtual: itensEntrega) {
            itemEntregaAtual.setItem(itemManager.findById(itemEntregaAtual.getItem().getId()).get());
            itemEntregaAtual.setEntregaDoacao(entregaDoacao);
            itensCompletos.add(itemEntregaAtual);
        }

        entregaDoacao.setItensEntrega(itensCompletos);
    }

    private void gravaItensEntrega(EntregaDoacao entregaDoacao){
        itemEntregaManager.saveAllItensEntrega(entregaDoacao.getItensEntrega());
    }

    private void atualizaItensEntrega(EntregaDoacao entregaDoacao){
        itemEntregaManager.updateAllItensEntrega(entregaDoacao.getItensEntrega(), entregaDoacao.getId());
    }

    private void movimentaEstoqueCancelamento(EntregaDoacao entregaDoacao){

        List<ItemEntregaDoacao> itensEntrega = entregaDoacao.getItensEntrega();
        for (ItemEntregaDoacao itemAtual: itensEntrega) {
            itemManager.validaAndMovimentaEstoque(itemAtual.getItem(), -itemAtual.getQuantidade());
        }

    }
}
