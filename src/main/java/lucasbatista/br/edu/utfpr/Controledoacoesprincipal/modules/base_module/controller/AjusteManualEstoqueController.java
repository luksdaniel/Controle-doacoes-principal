package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.controller;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptionHandler.EntityValidadeExceptionHandler;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.ajusteManualEstoque.AjusteManualEstoque;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.ajusteManualEstoque.AjusteManualEstoqueManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ajuste-manual-estoque")
public class AjusteManualEstoqueController extends EntityValidadeExceptionHandler {

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

}
