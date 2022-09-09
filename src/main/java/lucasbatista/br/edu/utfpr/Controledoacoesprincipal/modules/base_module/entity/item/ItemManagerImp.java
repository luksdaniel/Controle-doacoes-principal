package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.item;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceCreateError;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.controller.ItemController;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.persistence.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ItemManagerImp implements ItemManager {

    @Autowired
    ItemService itemService;

    @Override
    public List<Item> findAllItem() {
        List<Item> itemList = itemService.findAllItem();
        if(itemList.isEmpty()){
            throw new ResourceNotFoundException("Item não encontrado");
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
        Item itemInterno = itemService.saveItem(item);
        if(itemInterno == null){
            throw new ResourceCreateError("Não foi possível criar o item");
        }else{
            return itemInterno;
        }
    }

    @Override
    public Item updateItem(Item item) {
        if(itemService.findById(item.getId()).isEmpty()){
            throw new ResourceNotFoundException("Item não encontrado");
        } else {
            Item itemInterno = (itemService.updateItem(item));
            return itemInterno;
        }

    }

    @Override
    public void deleteItem(Long id) {
        if(itemService.findById(id).isEmpty()){
            throw new ResourceNotFoundException("Item não encontrado");
        } else {
            itemService.deleteItem(id);
        }
    }
}
