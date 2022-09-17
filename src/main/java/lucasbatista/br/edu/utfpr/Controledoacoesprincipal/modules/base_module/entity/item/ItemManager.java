package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.item;

import java.util.List;
import java.util.Optional;

public interface ItemManager {

    List<Item> findAllItem();
    Optional<Item> findById(Long id);
    Item saveItem(Item item);
    Item updateItem(Item item);
    void deleteItem(Long id);
    void validaAndMovimentaEstoque(Item item, double quantidade);

}
