package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.item;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.DependencyNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceCreateErrorException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.unidadeMedida.UnidadeMedidaManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.persistence.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class ItemManagerImp implements ItemManager {

    @Autowired
    ItemService itemService;

    @Autowired
    UnidadeMedidaManager unidadeMedidaManager;

    @Override
    public List<Item> findAllItem() {
        List<Item> itemList = itemService.findAllItem();
        if(itemList.isEmpty()){
            throw new ResourceNotFoundException("Itens não encontrados");
        }else{
            return itemList;
        }
    }

    @Override
    public Optional<Item> findById(Long id) {
        Optional<Item> item = itemService.findById(id);
        if(item.isEmpty()){
            throw new ResourceNotFoundException("Item não encontrado");
        }else{
            return item;
        }
    }

    @Override
    public Item saveItem(Item item) {
        validaUnidadeMedidaExistente(item);

        item.setUnidadeMedida(unidadeMedidaManager.findById(item.getUnidadeMedida().getId()).get());
        item.setDataCadastro(LocalDate.now());
        Item itemInterno = itemService.saveItem(item);
        if(itemInterno == null){
            throw new ResourceCreateErrorException("Não foi possível criar o item");
        }else{
            return itemInterno;
        }
    }

    @Override
    public Item updateItem(Item item) {
        validaUnidadeMedidaExistente(item);

        verificaItemJaCadastrado(item.getId());
        return (itemService.updateItem(item));
    }

    @Override
    public void deleteItem(Long id) {
        verificaItemJaCadastrado(id);
        itemService.deleteItem(id);
    }

    private void validaUnidadeMedidaExistente(Item item){
        if (unidadeMedidaManager.findById(item.getUnidadeMedida().getId()).isEmpty())
            throw new DependencyNotFoundException("Não foi localizada a unidade de medida informada");

    }

    private void verificaItemJaCadastrado(Long id){
        if(itemService.findById(id).isEmpty())
            throw new ResourceNotFoundException("Item não encontrado");
    }
}
