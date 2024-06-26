package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.persistence.item;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.item.Item;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemImplService implements ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Override
    public List<Item> findAllItem() {
        return itemRepository.findAll();
    }

    @Override
    public List<Item> findAllActiveItem() {
        return itemRepository.findByEstaCanceladoFalse();
    }

    @Override
    public Optional<Item> findById(Long id) {
        return itemRepository.findById(id);
    }

    @Override
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Item updateItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}
