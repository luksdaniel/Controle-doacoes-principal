package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.service.itemEntregaDoacao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.service.item.ItemManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.ItemEntregaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.repository.ItemEntregaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class ItemEntregaService implements ItemEntregaServiceBase {

    ItemEntregaRepository itemEntregaRepository;
    ItemManager itemManager;

    @Autowired
    public ItemEntregaService(ItemEntregaRepository itemEntregaRepository, ItemManager itemManager) {
        this.itemEntregaRepository = itemEntregaRepository;
        this.itemManager = itemManager;
    }

    @Override
    public List<ItemEntregaDoacao> saveAllItensEntrega(List<ItemEntregaDoacao> itens) {
        for (ItemEntregaDoacao itemAtual: itens) {
            if (itemAtual.getDataInclusao() == null)
                itemAtual.setDataInclusao(LocalDate.now());
        }

        return itemEntregaRepository.saveAll(itens);
    }

    @Override
    public ItemEntregaDoacao saveItenEntrega(ItemEntregaDoacao item) {

        if (item.getDataInclusao() == null)
            item.setDataInclusao(LocalDate.now());

        return itemEntregaRepository.save(item);
    }

    @Override
    public ItemEntregaDoacao findByIdd(Long id) {
        Optional<ItemEntregaDoacao> item = itemEntregaRepository.findById(id);
        if(!item.isPresent()){
            throw new ResourceNotFoundException("Item não encontrado na Entrega de doação");
        }else{
            return item.get();
        }
    }

    @Override
    public List<ItemEntregaDoacao> updateAllItensEntrega(List<ItemEntregaDoacao> itens, long entregaId) {

        List<ItemEntregaDoacao> listInternoEntrega = itemEntregaRepository.findByEntregaDoacaoId(entregaId);
        boolean gravadoAndAtualizado;

        for (ItemEntregaDoacao itemAtual: itens) {

            gravadoAndAtualizado = listInternoEntrega.contains(itemAtual);

            if(gravadoAndAtualizado){
                if (itemAtual.getDataInclusao() == null)
                    itemAtual.setDataInclusao(LocalDate.now());

                ItemEntregaDoacao itemAntigo = listInternoEntrega.get(listInternoEntrega.indexOf(itemAtual));
                double diferencaQtd = itemAtual.getQuantidade() - itemAntigo.getQuantidade();

                itemEntregaRepository.save(itemAtual);

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
                itemEntregaRepository.deleteById(itemAtual.getId());
                itemManager.validaAndMovimentaEstoque(itemAtual.getItem(), itemAtual.getQuantidade());
            }
        }
        return itens;
    }
}
