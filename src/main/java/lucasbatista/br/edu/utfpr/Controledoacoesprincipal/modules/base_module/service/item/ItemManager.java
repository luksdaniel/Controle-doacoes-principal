package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.service.item;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.Item;

import java.util.List;
import java.util.Optional;

public interface ItemManager {

    List<Item> findAllItem();
    List<Item> findAllActiveItem();
    Optional<Item> findById(Long id);
    Item saveItem(Item item);
    Item updateItem(Item item);
    Item cancelItem(Long id);
    Item uncanceItem(Long id);
    void validaAndMovimentaEstoque(Item item, double quantidade);

}
