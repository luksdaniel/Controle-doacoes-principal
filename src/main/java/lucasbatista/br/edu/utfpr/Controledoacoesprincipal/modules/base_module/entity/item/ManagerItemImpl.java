package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.item;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.persistence.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ManagerItemImpl implements ManagerItem{

    @Autowired
    ItemService itemService;

    @Override
    public List<Item> findAllItem() {
        return itemService.findAllItem();
    }

    @Override
    public Optional<Item> findById(Long id) {
        return itemService.findById(id);
    }

    @Override
    public Item saveItem(Item item) {
        return itemService.saveItem(item);
    }

    @Override
    public Item updateItem(Item item) {
        return itemService.updateItem(item);
    }

    @Override
    public void deleteItem(Long id) {
        itemService.deleteItem(id);
    }
}
