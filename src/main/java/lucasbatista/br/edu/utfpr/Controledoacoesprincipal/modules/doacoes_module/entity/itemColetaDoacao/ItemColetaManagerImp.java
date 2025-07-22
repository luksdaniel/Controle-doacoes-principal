package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.itemColetaDoacao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.itemColetaDoacao.ItemColetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class ItemColetaManagerImp implements ItemColetaManager{

    @Autowired
    ItemColetaService itemColetaService;

    @Override
    public List<ItemColetaDoacao> saveAllItensColeta(List<ItemColetaDoacao> itens) {
        for (ItemColetaDoacao itemAtual: itens) {
            if (itemAtual.getDataInclusao() == null)
                itemAtual.setDataInclusao(LocalDate.now());
        }

        return itemColetaService.saveItemAllColetaDoacao(itens);
    }

    @Override
    public ItemColetaDoacao saveItenColeta(ItemColetaDoacao item) {

        if (item.getDataInclusao() == null)
            item.setDataInclusao(LocalDate.now());

        return itemColetaService.saveItenColeta(item);
    }


    @Override
    public ItemColetaDoacao findByIdd(Long id) {
        Optional<ItemColetaDoacao> item = itemColetaService.findById(id);
        if(!item.isPresent()){
            throw new ResourceNotFoundException("Item não encontrado na doação");
        }else{
            return item.get();
        }
    }

    @Override
    public List<ItemColetaDoacao> updateAllItensColeta(List<ItemColetaDoacao> itens, long coletaId) {

        List<ItemColetaDoacao> listInternoColeta = itemColetaService.findByColetaId(coletaId);
        boolean gravadoAndAtualizado;

        for (ItemColetaDoacao itemAtual: itens) {

            gravadoAndAtualizado = listInternoColeta.contains(itemAtual);

            if(gravadoAndAtualizado){
                if (itemAtual.getDataInclusao() == null)
                    itemAtual.setDataInclusao(LocalDate.now());
                itemColetaService.updateItemColetaDoacao(itemAtual);
            }else {
                saveItenColeta(itemAtual);
            }

        }
        for (ItemColetaDoacao itemAtual: listInternoColeta){

            gravadoAndAtualizado = itens.contains(itemAtual);

            if(!gravadoAndAtualizado)
                itemColetaService.deleteItemColetaDoacao(itemAtual.getId());

        }

        return itens;
    }
}
