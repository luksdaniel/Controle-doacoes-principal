package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.controller;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptionHandler.EntityValidadeExceptionHandler;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.usuario.Usuario;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.usuario.UsuarioManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController extends EntityValidadeExceptionHandler {

    @Autowired
    UsuarioManager usuarioManager;

    @GetMapping
    public ResponseEntity<List<Usuario>> findAllUsuario() {
        List<Usuario> usuarioList = usuarioManager.findAllUsuario();
        for(Usuario usuario : usuarioList){
            usuario.removeLinks();
            long id = usuario.getId();
            usuario.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).findUsuarioById((Long) id)).withSelfRel());
        }
        return new ResponseEntity<>(usuarioList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findUsuarioById(@PathVariable("id") Long id){
        Optional<Usuario> usuario = usuarioManager.findById(id);

        usuario.get().removeLinks();
        usuario.get().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).findAllUsuario()).withRel("Lista de Usuarios"));
        return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Usuario> saveUsuario(@RequestBody @Valid Usuario usuario){
        Usuario usuarioInterno = usuarioManager.saveUsuario(usuario);
        usuarioInterno.removeLinks();
        usuarioInterno.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).findAllUsuario()).withRel("Lista de Usuarios"));
        return new ResponseEntity<Usuario>(usuarioInterno, HttpStatus.CREATED);

    }

    @PutMapping
    public ResponseEntity<Usuario> updateUsuario(@RequestBody @Valid Usuario usuario){
        Usuario usuarioInterno = (usuarioManager.updateUsuario(usuario));
        usuarioInterno.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).findAllUsuario()).withRel("Lista de itens"));
        return new ResponseEntity<Usuario>(usuarioInterno, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<Usuario>> deleteUsuario(@PathVariable("id") Long id){
        usuarioManager.deleteUsuario(id);
        return findAllUsuario();
    }
}
