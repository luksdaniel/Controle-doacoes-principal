package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.controller;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.Item;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.persistence.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    ItemService itemService;

    @GetMapping
    public ResponseEntity<List<Item>> findAllItem() {
        List<Item> itemList = itemService.findAllItem();
        if(itemList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            for(Item item : itemList){
                item.removeLinks();
                long id = item.getId();
                item.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ItemController.class).findItemById((Long) id)).withSelfRel());
            }
            return new ResponseEntity<>(itemList, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> findItemById(@PathVariable("id") Long id){
        Optional<Item> item = itemService.findById(id);
        if(item.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            item.get().removeLinks();
            item.get().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ItemController.class).findAllItem()).withRel("Lista de Items"));
            return new ResponseEntity<Item>(item.get(), HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<Item> saveItem(@RequestBody Item item){
        Item itemInterno = itemService.saveItem(item);
        if(item == null){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }else{
            itemInterno.removeLinks();
            itemInterno.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ItemController.class).findAllItem()).withRel("Lista de Items"));
            return new ResponseEntity<Item>(itemInterno, HttpStatus.OK);
        }

    }

    @PutMapping
    public ResponseEntity<Item> updateItem(@RequestBody Item item){
        if(itemService.findById(item.getId()).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Item itemInterno = (itemService.updateItem(item));
            itemInterno.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ItemController.class).findAllItem()).withRel("Lista de itens"));
            return new ResponseEntity<Item>(itemInterno, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<Item>> deleteItem(@PathVariable("id") Long id){
        try{
            itemService.deleteItem(id);
            return findAllItem();
        }catch (Exception ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

    }
}
