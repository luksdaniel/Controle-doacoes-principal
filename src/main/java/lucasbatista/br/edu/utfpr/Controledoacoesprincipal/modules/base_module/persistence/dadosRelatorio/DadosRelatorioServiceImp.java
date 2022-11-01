package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.persistence.dadosRelatorio;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.dto.ColetaEntregaDto;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.dto.MovItemDto;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.item.Item;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.persistence.item.ItemService;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.coletaDoacao.ColetaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.entregaDoacao.EntregaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.itemColetaDoacao.ItemColetaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.itemEntregaDoacao.ItemEntregaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.coletaDoacao.ColetaDoacaoService;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.entregaDoacao.EntregaDoacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DadosRelatorioServiceImp implements DadosRelatorioService{

    @Autowired
    ItemService itemService;

    @Autowired
    EntregaDoacaoService entregaDoacaoService;

    @Autowired
    ColetaDoacaoService coletaDoacaoService;

    public List<MovItemDto> findAllMovimentacoes(){

        List<MovItemDto> movimentacoes = new ArrayList<>();
        List<Item> itens = itemService.findAllItem();

        for (Item item: itens){

        }

        return null;
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

        return movimentacoes;
    }

}
