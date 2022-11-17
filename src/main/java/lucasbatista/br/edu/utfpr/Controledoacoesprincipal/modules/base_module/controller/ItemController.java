package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.controller;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptionHandler.EntityValidateExceptionHandler;
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
public class ItemController extends EntityValidateExceptionHandler {
    @Autowired
    ItemManager managerItem;

    @GetMapping("/{id}")
    public ResponseEntity<Item> findItemById(@PathVariable("id") Long id){
        Optional<Item> item = managerItem.findById(id);

        return new ResponseEntity<Item>(item.get(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Item>> findAllItem() {
        List<Item> itemList = managerItem.findAllItem();

        return new ResponseEntity<>(itemList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Item> saveItem(@RequestBody @Valid Item item){
        Item itemInterno = managerItem.saveItem(item);

        return new ResponseEntity<Item>(itemInterno, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Item> updateItem(@RequestBody @Valid Item item){
        Item itemInterno = (managerItem.updateItem(item));

        return new ResponseEntity<Item>(itemInterno, HttpStatus.OK);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<Item> cancelItem(@PathVariable("id") Long id){
        Item item = managerItem.cancelItem(id);

        return new ResponseEntity<Item>(item, HttpStatus.OK);
    }

    @PutMapping("/uncancel/{id}")
    public ResponseEntity<Item> uncancelItem(@PathVariable("id") Long id){
        Item item = managerItem.uncanceItem(id);

        return new ResponseEntity<Item>(item, HttpStatus.OK);
    }

}
