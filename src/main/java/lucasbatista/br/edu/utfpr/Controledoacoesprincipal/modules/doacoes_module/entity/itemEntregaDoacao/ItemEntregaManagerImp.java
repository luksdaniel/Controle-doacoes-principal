package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.itemEntregaDoacao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.item.ItemManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.entregaDoacao.EntregaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.itemColetaDoacao.ItemColetaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.itemEntregaDoacao.ItemEntregaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class ItemEntregaManagerImp implements ItemEntregaManager{

    @Autowired
    ItemEntregaService itemEntregaService;

    @Autowired
    ItemManager itemManager;

    @Override
    public List<ItemEntregaDoacao> saveAllItensEntrega(List<ItemEntregaDoacao> itens) {
        for (ItemEntregaDoacao itemAtual: itens) {
            if (itemAtual.getDataInclusao() == null)
                itemAtual.setDataInclusao(LocalDate.now());
        }

        return itemEntregaService.saveAllItemEntrega(itens);
    }

    @Override
    public ItemEntregaDoacao saveItenEntrega(ItemEntregaDoacao item) {

        if (item.getDataInclusao() == null)
            item.setDataInclusao(LocalDate.now());

        return itemEntregaService.saveItemEntrega(item);
    }

    @Override
    public ItemEntregaDoacao findByIdd(Long id) {
        Optional<ItemEntregaDoacao> item = itemEntregaService.findById(id);
        if(item.isEmpty()){
            throw new ResourceNotFoundException("Item não encontrado na Entrega de doação");
        }else{
            return item.get();
        }
    }

    @Override
    public List<ItemEntregaDoacao> updateAllItensEntrega(List<ItemEntregaDoacao> itens, long entregaId) {

        List<ItemEntregaDoacao> listInternoEntrega = itemEntregaService.findByEntregaId(entregaId);
        boolean gravadoAndAtualizado;

        for (ItemEntregaDoacao itemAtual: itens) {

            gravadoAndAtualizado = listInternoEntrega.contains(itemAtual);

            if(gravadoAndAtualizado){
                if (itemAtual.getDataInclusao() == null)
                    itemAtual.setDataInclusao(LocalDate.now());

                ItemEntregaDoacao itemAntigo = listInternoEntrega.get(listInternoEntrega.indexOf(itemAtual));
                double diferencaQtd = itemAtual.getQuantidade() - itemAntigo.getQuantidade();

                itemEntregaService.updateItemEntrega(itemAtual);

                if(diferencaQtd != 0)
                    itemManager.validaAndMovimentaEstoque(itemAtual.getItem(), -diferencaQtd);

            }else {
                saveItenEntrega(itemAtual);
                itemManager.validaAndMovimentaEstoque(itemAtual.getItem(), -itemAtual.getQuantidade());
            }
        }
        for (ItemEntregaDoacao itemAtual: listInternoEntrega){

            gravadoAndAtualizado = itens.contains(itemAtual);

            if(!gravadoAndAtualizado) {
                itemEntregaService.deleteItemEntregaDoacao(itemAtual.getId());
                itemManager.validaAndMovimentaEstoque(itemAtual.getItem(), itemAtual.getQuantidade());
            }
        }
        return itens;
    }
}
