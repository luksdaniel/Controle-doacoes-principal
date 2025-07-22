package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.controller;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptionHandler.EntityValidateExceptionHandler;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.dto.EmailDto;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.dto.PasswordDto;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.dto.RecoveryCredentialsDto;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.entity.usuario.Usuario;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.entity.usuario.UsuarioManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.persistence.recoveryToken.RecoveryTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController extends EntityValidateExceptionHandler {

    @Autowired
    UsuarioManager usuarioManager;

    @Autowired
    RecoveryTokenService recoveryTokenService;

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

    @GetMapping("/username/{username}")
    public ResponseEntity<Usuario> findUsuarioByUsername(@PathVariable("username") String username){
        Optional<Usuario> usuario = usuarioManager.findByUserName(username);

        usuario.get().removeLinks();
        usuario.get().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).findAllUsuario()).withRel("Lista de Usuarios"));
        return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);

    }

    @GetMapping("/doador/{id}")
    public ResponseEntity<Usuario> findByDoadorId(@PathVariable("id") Long id){
        Optional<Usuario> usuario = usuarioManager.findByDoadorId(id);

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

    /*@PutMapping
    public ResponseEntity<Usuario> updateUsuario(@RequestBody Usuario usuario){
        Usuario usuarioInterno = (usuarioManager.updateUsuario(usuario));
        usuarioInterno.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).findAllUsuario()).withRel("Lista de itens"));
        return new ResponseEntity<Usuario>(usuarioInterno, HttpStatus.OK);
    }*/

    @PutMapping("/username/{id}")
    public ResponseEntity<Usuario> updateUsername(@RequestBody String username, @PathVariable("id") Long id){
        Usuario usuarioInterno = (usuarioManager.updateUsername(username, id));

        return new ResponseEntity<Usuario>(usuarioInterno, HttpStatus.OK);
    }

    @PutMapping("/password/{id}")
    public ResponseEntity<Usuario> updatePassword(@RequestBody PasswordDto passwordDto, @PathVariable("id") Long id){
        Usuario usuarioInterno = (usuarioManager.updatePassword(passwordDto, id));

        return new ResponseEntity<Usuario>(usuarioInterno, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<Usuario>> deleteUsuario(@PathVariable("id") Long id){
        usuarioManager.deleteUsuario(id);
        return findAllUsuario();
    }

    @PostMapping("/password-recovery")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void passwordRecovery(@RequestBody EmailDto email) {
        recoveryTokenService.createToken(email.getEmail());
    }

    @PostMapping("/recovery-confirm")
    public void recoveryConfirm(@RequestBody RecoveryCredentialsDto recoveryCredentialsDto) {
        recoveryTokenService.recoverPassword(recoveryCredentialsDto);
    }

    @PostMapping("/recovery-token-valid")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void validateRecoveryToken(@RequestBody RecoveryCredentialsDto recoveryCredentialsDto) {
        recoveryTokenService.validateRecoveryToken(recoveryCredentialsDto);
    }
}
