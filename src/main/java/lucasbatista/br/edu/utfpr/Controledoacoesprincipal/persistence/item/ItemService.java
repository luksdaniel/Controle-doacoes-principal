package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.persistence.item;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.entity.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    List<Item> findAllItem();
    Optional<Item> findById(Long id);
    Item saveItem(Item item);
    Item updateItem(Item item);
    void deleteItem(Long id);

}
