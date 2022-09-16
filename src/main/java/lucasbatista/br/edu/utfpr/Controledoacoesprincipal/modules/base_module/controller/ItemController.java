package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.controller;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptionHandler.EntityValidadeExceptionHandler;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.item.Item;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.item.ItemManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/item")
public class ItemController extends EntityValidadeExceptionHandler {
    @Autowired
    ItemManager managerItem;

    @GetMapping
    public ResponseEntity<List<Item>> findAllItem() {
        List<Item> itemList = managerItem.findAllItem();
        for(Item item : itemList){
            item.removeLinks();
            long id = item.getId();
            item.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ItemController.class).findItemById((Long) id)).withSelfRel());
        }
        return new ResponseEntity<>(itemList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> findItemById(@PathVariable("id") Long id){
        Optional<Item> item = managerItem.findById(id);

        item.get().removeLinks();
        item.get().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ItemController.class).findAllItem()).withRel("Lista de Items"));
        return new ResponseEntity<Item>(item.get(), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Item> saveItem(@RequestBody @Valid Item item){
        Item itemInterno = managerItem.saveItem(item);
        itemInterno.removeLinks();
        itemInterno.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ItemController.class).findAllItem()).withRel("Lista de Items"));
        return new ResponseEntity<Item>(itemInterno, HttpStatus.CREATED);

    }

    @PutMapping
    public ResponseEntity<Item> updateItem(@RequestBody @Valid Item item){
        Item itemInterno = (managerItem.updateItem(item));
        itemInterno.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ItemController.class).findAllItem()).withRel("Lista de itens"));
        return new ResponseEntity<Item>(itemInterno, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<Item>> deleteItem(@PathVariable("id") Long id){
        managerItem.deleteItem(id);
        return findAllItem();
    }

}
