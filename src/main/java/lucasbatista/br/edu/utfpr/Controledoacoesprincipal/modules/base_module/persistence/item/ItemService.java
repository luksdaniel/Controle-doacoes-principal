package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.persistence.item;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.item.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    List<Item> findAllItem();

    List<Item> findAllActiveItem();
    Optional<Item> findById(Long id);
    Item saveItem(Item item);
    Item updateItem(Item item);
    void deleteItem(Long id);

}
