package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.controller;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptionHandler.EntityValidateExceptionHandler;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.unidadeMedida.UnidadeMedidaManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.unidadeMedida.UnidadeMedida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/unidadeMedida")
public class UnidadeMedidaController extends EntityValidateExceptionHandler {

    @Autowired
    UnidadeMedidaManager managerUnidadeMedida;

    @GetMapping
    public ResponseEntity<List<UnidadeMedida>> findAllUnidadeMedida() {
        List<UnidadeMedida> unidadeMedidaList = managerUnidadeMedida.findAllUnidadeMedida();
        for(UnidadeMedida un : unidadeMedidaList){
            un.removeLinks();
            long id = un.getId();
            un.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UnidadeMedidaController.class).findUnidadeMedidaById((Long) id)).withSelfRel());
        }
        return new ResponseEntity<>(unidadeMedidaList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnidadeMedida> findUnidadeMedidaById(@PathVariable("id") Long id){
        Optional<UnidadeMedida> un = managerUnidadeMedida.findById(id);
        un.get().removeLinks();
        un.get().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UnidadeMedidaController.class).findAllUnidadeMedida()).withRel("Lista de Unidades de medida"));
        return new ResponseEntity<UnidadeMedida>(un.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UnidadeMedida> saveUnidadeMedida(@RequestBody @Valid UnidadeMedida unidadeMedida){
        UnidadeMedida un = managerUnidadeMedida.saveUnidadeMedida(unidadeMedida);

        un.removeLinks();
        un.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UnidadeMedidaController.class).findAllUnidadeMedida()).withRel("Lista de Unidades de medida"));
        return new ResponseEntity<UnidadeMedida>(un, HttpStatus.CREATED);

    }

    @PutMapping
    public ResponseEntity<UnidadeMedida> updateUnidadeMedida(@RequestBody @Valid UnidadeMedida unidadeMedida){
        UnidadeMedida un = (managerUnidadeMedida.updaUnidadeMedida(unidadeMedida));
        un.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UnidadeMedidaController.class).findAllUnidadeMedida()).withRel("Lista de Unidades de medida"));
        return new ResponseEntity<UnidadeMedida>(un, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<UnidadeMedida>> deleteUnidadeMedida(@PathVariable("id") Long id){
        managerUnidadeMedida.deleteUnidadeMedida(id);
        return findAllUnidadeMedida();
    }

}
