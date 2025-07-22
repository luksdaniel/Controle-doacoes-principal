package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.controller;

import jakarta.validation.Valid;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptionHandler.EntityValidateExceptionHandler;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.ajusteManualEstoque.AjusteManualEstoque;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.ajusteManualEstoque.AjusteManualEstoqueManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ajuste-manual-estoque")
public class AjusteManualEstoqueController extends EntityValidateExceptionHandler {

    @Autowired
    AjusteManualEstoqueManager ajusteManualEstoqueManager;

    @GetMapping("item/{Id}")
    public ResponseEntity<List<AjusteManualEstoque>> findUsuarioByItemId(@PathVariable("Id") Long id){
        List<AjusteManualEstoque> ajusteManualEstoque = ajusteManualEstoqueManager.findByItemId(id);

        /*ajusteManualEstoque.get().removeLinks();
        ajusteManualEstoque.get().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).findAllUsuario()).withRel("Lista de Usuarios"));*/
        return new ResponseEntity<>(ajusteManualEstoque, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<AjusteManualEstoque> saveAjusteManual(@RequestBody @Valid AjusteManualEstoque ajusteManualEstoque){
        AjusteManualEstoque ajusteManualInterno = ajusteManualEstoqueManager.saveAjusteManual(ajusteManualEstoque);
        ajusteManualInterno.removeLinks();
        //usuarioInterno.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AjusteManualEstoqueController.class).findAllUsuario()).withRel("Lista de Usuarios"));
        return new ResponseEntity<>(ajusteManualInterno, HttpStatus.CREATED);
    }

    @PutMapping("cancel/{id}")
    public ResponseEntity<AjusteManualEstoque> cancelaAjusteManual(@PathVariable("id") Long id){
        AjusteManualEstoque ajusteManualInterno = ajusteManualEstoqueManager.cancelaAjusteManual(id);
        //ajusteManualInterno.removeLinks();
        //usuarioInterno.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AjusteManualEstoqueController.class).findAllUsuario()).withRel("Lista de Usuarios"));
        return new ResponseEntity<>(ajusteManualInterno, HttpStatus.OK);
    }

}
