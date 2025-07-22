package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.service.relatorio;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.dto.ColetaEntregaDto;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.dto.MovItemDto;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.AjusteManualEstoque;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.service.item.ItemService;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.service.ajusteManual.AjusteManualService;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.coletaDoacao.ColetaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.entregaDoacao.EntregaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.itemColetaDoacao.ItemColetaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.itemEntregaDoacao.ItemEntregaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.coletaDoacao.ColetaDoacaoService;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.entregaDoacao.EntregaDoacaoService;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.itemColetaDoacao.ItemColetaService;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.itemEntregaDoacao.ItemEntregaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RelatorioService implements RelatorioServiceBase {

    ItemService itemService;
    EntregaDoacaoService entregaDoacaoService;
    ColetaDoacaoService coletaDoacaoService;
    ItemColetaService itemColetaService;
    ItemEntregaService itemEntregaService;
    AjusteManualService ajusteManualService;

    @Autowired
    public RelatorioService(
            ItemService itemService,
            EntregaDoacaoService entregaDoacaoService,
            ColetaDoacaoService coletaDoacaoService,
            ItemColetaService itemColetaService,
            ItemEntregaService itemEntregaService,
            AjusteManualService ajusteManualService) {
        this.itemService = itemService;
        this.entregaDoacaoService = entregaDoacaoService;
        this.coletaDoacaoService = coletaDoacaoService;
        this.itemColetaService = itemColetaService;
        this.itemEntregaService = itemEntregaService;
        this.ajusteManualService = ajusteManualService;
    }

    @Override
    public List<MovItemDto> findAllMovimentacoes(long itemId){

        List<MovItemDto> movimentacoes = new ArrayList<>();
        List<ItemColetaDoacao> itensColeta = itemColetaService.findByItemId(itemId);
        List<ColetaDoacao> coletaList = new ArrayList<>();

        List<ItemEntregaDoacao> itensEntrega = itemEntregaService.findByItemId(itemId);
        List<EntregaDoacao> entregaLista = new ArrayList<>();

        List<AjusteManualEstoque> ajustesList = ajusteManualService.findByItemId(itemId);

        if(ajustesList.isEmpty() && itensColeta.isEmpty() && itensEntrega.isEmpty())
            throw new ResourceNotFoundException("Não foram encontradas movimentações para o item!");

        for (ItemColetaDoacao item: itensColeta){
            if(!coletaList.contains(item.getColetaDoacao()) && item.getColetaDoacao().isEstaEfetivada() && !item.getColetaDoacao().isEstaCancelada()){
                coletaList.add(item.getColetaDoacao());
                MovItemDto mov = new MovItemDto();
                mov.setDataMovimentacao(item.getColetaDoacao().getDataEfetivacao());
                mov.setDescricaoItem(item.getItem().getDescricao());
                mov.setTipoDocumento("Coleta de doação");
                mov.setQuantidadeMovimentada(item.getQuantidade());
                movimentacoes.add(mov);
            }
        }

        for (ItemEntregaDoacao item: itensEntrega){
            if(!entregaLista.contains(item.getEntregaDoacao()) && !item.getEntregaDoacao().isEstaCancelada()){
                entregaLista.add(item.getEntregaDoacao());
                MovItemDto mov = new MovItemDto();
                mov.setDataMovimentacao(item.getEntregaDoacao().getDataEntrega());
                mov.setDescricaoItem(item.getItem().getDescricao());
                mov.setTipoDocumento("Entrega de doação");
                mov.setQuantidadeMovimentada(-item.getQuantidade());
                movimentacoes.add(mov);
            }
        }

        for (AjusteManualEstoque ajuste: ajustesList){
            MovItemDto mov = new MovItemDto();
            mov.setDataMovimentacao(ajuste.getDataAjuste());
            mov.setDescricaoItem(ajuste.getItem().getDescricao());
            mov.setTipoDocumento("Ajuste Manual de estoque");
            mov.setQuantidadeMovimentada(ajuste.getQuantidadeMovimentada());
            movimentacoes.add(mov);
        }

        Collections.sort(movimentacoes);
        return movimentacoes;
    }

    @Override
    public List<ColetaEntregaDto> getDadosRelatorioColetaEntrega(){

        List<ColetaEntregaDto> movimentacoes = new ArrayList<>();
        List<ColetaDoacao> coletas = coletaDoacaoService.findAllColetaDoacao();
        List<EntregaDoacao> entregas = entregaDoacaoService.findAllEntregaDoacao();
        ColetaEntregaDto mov;

        for (ColetaDoacao coletaAtual: coletas){
            if (coletaAtual.isEstaEfetivada()){
                for (ItemColetaDoacao itemAtual : coletaAtual.getItensColeta()){
                    mov = new ColetaEntregaDto();
                    mov.setData(coletaAtual.getDataEfetivacao());
                    mov.setDoadorBeneficiario(coletaAtual.getDoador().getNomeFantasia());
                    mov.setTipoDocumento("Coleta de doação");
                    mov.setDescricaoItem(itemAtual.getItem().getDescricao());
                    mov.setQuantidadeMovimentada(itemAtual.getQuantidade());
                    movimentacoes.add(mov);
                }
            }
        }

        for (EntregaDoacao entregaAtual: entregas){

            for (ItemEntregaDoacao itemAtual : entregaAtual.getItensEntrega()){
                mov = new ColetaEntregaDto();
                mov.setData(entregaAtual.getDataEntrega());
                mov.setDoadorBeneficiario(entregaAtual.getBeneficiario().getNomeFantasia());
                mov.setTipoDocumento("Entrega de doação");
                mov.setDescricaoItem(itemAtual.getItem().getDescricao());
                mov.setQuantidadeMovimentada(itemAtual.getQuantidade());
                movimentacoes.add(mov);
            }
        }

        Collections.sort(movimentacoes);
        return movimentacoes;
    }

}
