package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.controller;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.unidadeMedida.ManagerUnidadeMedida;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.unidadeMedida.UnidadeMedida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/unidadeMedida")
public class UnidadeMedidaController {

    @Autowired
    ManagerUnidadeMedida managerUnidadeMedida;

    @GetMapping
    public ResponseEntity<List<UnidadeMedida>> findAllUnidadeMedida() {
        List<UnidadeMedida> unidadeMedidaList = managerUnidadeMedida.findAllUnidadeMedida();
        if(unidadeMedidaList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            for(UnidadeMedida un : unidadeMedidaList){
                un.removeLinks();
                long id = un.getId();
                un.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UnidadeMedidaController.class).findUnidadeMedidaById((Long) id)).withSelfRel());
            }
            return new ResponseEntity<>(unidadeMedidaList, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnidadeMedida> findUnidadeMedidaById(@PathVariable("id") Long id){
        Optional<UnidadeMedida> un = managerUnidadeMedida.findById(id);
        if(un.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            un.get().removeLinks();
            un.get().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UnidadeMedidaController.class).findAllUnidadeMedida()).withRel("Lista de Unidades de medida"));
            return new ResponseEntity<UnidadeMedida>(un.get(), HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<UnidadeMedida> saveUnidadeMedida(@RequestBody UnidadeMedida unidadeMedida){
        UnidadeMedida un = managerUnidadeMedida.saveUnidadeMedida(unidadeMedida);
        if(unidadeMedida == null){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }else{
            un.removeLinks();
            un.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UnidadeMedidaController.class).findAllUnidadeMedida()).withRel("Lista de Unidades de medida"));
            return new ResponseEntity<UnidadeMedida>(un, HttpStatus.OK);
        }

    }

    @PutMapping
    public ResponseEntity<UnidadeMedida> updateUnidadeMedida(@RequestBody UnidadeMedida unidadeMedida){
        if(managerUnidadeMedida.findById(unidadeMedida.getId()).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            UnidadeMedida un = (managerUnidadeMedida.updaUnidadeMedida(unidadeMedida));
            un.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UnidadeMedidaController.class).findAllUnidadeMedida()).withRel("Lista de Unidades de medida"));
            return new ResponseEntity<UnidadeMedida>(un, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<UnidadeMedida>> deleteUnidadeMedida(@PathVariable("id") Long id){
        try{
            managerUnidadeMedida.deleteUnidadeMedida(id);
            return findAllUnidadeMedida();
        }catch (Exception ex){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }

}
