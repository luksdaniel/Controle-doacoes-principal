package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.service.entregaDoacao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.BusinessException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.Instituicao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.service.instituicao.InstituicaoServiceBase;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.service.item.ItemManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.Beneficiario;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.EntregaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.ItemEntregaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.repository.EntregaDoacaoRepository;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.service.beneficiario.BeneficiarioServiceBase;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.service.itemEntregaDoacao.ItemEntregaServiceBase;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.entity.Usuario;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.service.usuario.UsuarioServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class EntregaDoacaoService implements EntregaDoacaoServiceBase {

    EntregaDoacaoRepository entregaDoacaoRepository;
    UsuarioServiceBase usuarioServiceBase;
    BeneficiarioServiceBase beneficiarioService;
    ItemManager itemManager;
    ItemEntregaServiceBase itemEntregaServiceBase;
    InstituicaoServiceBase instituicaoServiceBase;

    @Autowired
    public EntregaDoacaoService(EntregaDoacaoRepository entregaDoacaoRepository, UsuarioServiceBase usuarioServiceBase, BeneficiarioServiceBase beneficiarioService, ItemManager itemManager, ItemEntregaServiceBase itemEntregaServiceBase, InstituicaoServiceBase instituicaoServiceBase) {
        this.entregaDoacaoRepository = entregaDoacaoRepository;
        this.usuarioServiceBase = usuarioServiceBase;
        this.beneficiarioService = beneficiarioService;
        this.itemManager = itemManager;
        this.itemEntregaServiceBase = itemEntregaServiceBase;
        this.instituicaoServiceBase = instituicaoServiceBase;
    }

    @Override
    public List<EntregaDoacao> findAllEntregaDoacao() {
        List<EntregaDoacao> entregaList = entregaDoacaoRepository.findAll();
        if (entregaList.isEmpty()){
            throw new ResourceNotFoundException("Entregas não encontradas");
        }else {
            return entregaList;
        }
    }

    @Override
    public List<EntregaDoacao> findByIdBeneficiario(Long id) {
        List<EntregaDoacao> entregaList = entregaDoacaoRepository.findByBeneficiarioId(id);

        if (entregaList.isEmpty()){
            throw new ResourceNotFoundException("Entregas não encontradas");
        }else {
            return entregaList;
        }
    }

    @Override
    public List<EntregaDoacao> findByIdUsuarioRegistro(Long id) {
        List<EntregaDoacao> entregaList = entregaDoacaoRepository.findByUsuarioRegistroId(id);

        if (entregaList.isEmpty()){
            throw new ResourceNotFoundException("Entregas não encontradas");
        }else {
            return entregaList;
        }
    }

    @Override
    public Optional<EntregaDoacao> findById(Long id) {
        Optional<EntregaDoacao> entregaList = entregaDoacaoRepository.findById(id);
        if(!entregaList.isPresent()){
            throw new ResourceNotFoundException("Entrega não encontrada");
        }else{
            return entregaList;
        }
    }

    @Override
    public EntregaDoacao saveEntregaDoacao(EntregaDoacao entregaDoacao) {
        setaAtributosIniciais(entregaDoacao);
        entregaDoacao.setEstaCancelada(false);

        validaDiasEntreDoacao(entregaDoacao);

        List<ItemEntregaDoacao> itensEntrega = entregaDoacao.getItensEntrega();
        EntregaDoacao entregaInterna = entregaDoacaoRepository.save(entregaDoacao);

        gravaItensEntrega(entregaDoacao);
        for (ItemEntregaDoacao itemAtual: itensEntrega) {
            itemManager.validaAndMovimentaEstoque(itemAtual.getItem(), -itemAtual.getQuantidade());
        }
        return entregaInterna;

    }

    @Override
    public EntregaDoacao updateEntregaDoacao(EntregaDoacao entregaDoacao) {
        Optional<EntregaDoacao> entregaDoacaoInterna = findById(entregaDoacao.getId());

        if (entregaDoacaoInterna.get().isEstaCancelada())
            throw new BusinessException("A entrega de doação está cancelada");

        setaAtributosIniciais(entregaDoacao);
        EntregaDoacao entregaDoacao1 = entregaDoacaoRepository.save(entregaDoacao);

        atualizaItensEntrega(entregaDoacao);
        return entregaDoacao1;

    }

    @Override
    public EntregaDoacao cancelaEntregaDoacao(EntregaDoacao entregaDoacao) {

        if (entregaDoacao.getMotivoCancelamento() == null || entregaDoacao.getMotivoCancelamento().isEmpty())
            throw new BusinessException("Deve ser informado o motivo de cancelamento");

        Optional<EntregaDoacao> entregaDoacaoInterna = findById(entregaDoacao.getId());
        Optional<Usuario> usuarioCancelamento = usuarioServiceBase.findById(entregaDoacao.getUsuarioCancelamento().getId());
        String motivoCancelamento = entregaDoacao.getMotivoCancelamento();

        if (entregaDoacaoInterna.get().isEstaCancelada())
            throw new BusinessException("A entrega de doação já está cancelada");

        movimentaEstoqueCancelamento(entregaDoacaoInterna.get());

        entregaDoacaoInterna.get().setMotivoCancelamento(motivoCancelamento);
        entregaDoacaoInterna.get().setEstaCancelada(true);
        entregaDoacaoInterna.get().setUsuarioCancelamento(usuarioCancelamento.get());

        return entregaDoacaoRepository.save(entregaDoacaoInterna.get());
    }

    private void validaDiasEntreDoacao(EntregaDoacao entregaDoacao){

        Optional<Beneficiario> beneficiario = beneficiarioService.findById(entregaDoacao.getBeneficiario().getId());
        EntregaDoacao ultimaEntrega = retornaUltimaEntregaBeneficiario(beneficiario.get().getId());
        Optional<Instituicao> instituicao = instituicaoServiceBase.find();
        int intervalo;

        if( beneficiario.get().getDiasEntreDoacao() != 0 ) {
            intervalo = beneficiario.get().getDiasEntreDoacao();
        }else {
            intervalo = instituicao.get().getDiasEntreDoacao();
        }

        if(ultimaEntrega == null){
            return;
        }
        if(ultimaEntrega.getDataEntrega().plusDays(intervalo).isAfter(LocalDate.now()) && !ultimaEntrega.isEstaCancelada()){
            throw new BusinessException("O Beneficiário já recebeu uma doação no dia ("+
                    ultimaEntrega.getDataEntrega()+") e só pode receber outra após ("+beneficiario.get().getDiasEntreDoacao()+") dias");
        }
    }

    private void setaAtributosIniciais(EntregaDoacao entregaDoacao){

        entregaDoacao.setDataEntrega(LocalDate.now());
        entregaDoacao.setUsuarioRegistro(usuarioServiceBase.findById(entregaDoacao.getUsuarioRegistro().getId()).get());
        entregaDoacao.setBeneficiario(beneficiarioService.findById(entregaDoacao.getBeneficiario().getId()).get());

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
        itemEntregaServiceBase.saveAllItensEntrega(entregaDoacao.getItensEntrega());
    }

    private void atualizaItensEntrega(EntregaDoacao entregaDoacao){
        itemEntregaServiceBase.updateAllItensEntrega(entregaDoacao.getItensEntrega(), entregaDoacao.getId());
    }

    private void movimentaEstoqueCancelamento(EntregaDoacao entregaDoacao){

        List<ItemEntregaDoacao> itensEntrega = entregaDoacao.getItensEntrega();
        for (ItemEntregaDoacao itemAtual: itensEntrega) {
            itemManager.validaAndMovimentaEstoque(itemAtual.getItem(), itemAtual.getQuantidade());
        }

    }

    public EntregaDoacao retornaUltimaEntregaBeneficiario(long id) {
        List<EntregaDoacao> entregaList = entregaDoacaoRepository.findByUsuarioRegistroIdOrderByDataEntregaDesc(id);
        EntregaDoacao entregaRetorno = new EntregaDoacao();

        if(entregaList.isEmpty())
            return null;

        entregaRetorno = entregaList.get(0);

        return entregaRetorno;
    }
}
