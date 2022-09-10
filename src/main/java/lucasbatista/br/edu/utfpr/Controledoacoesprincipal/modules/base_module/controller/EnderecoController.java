package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.controller;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptionHandler.EntityValidadeExceptionHandler;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.endereco.Endereco;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.endereco.EnderecoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/endereco")
public class EnderecoController extends EntityValidadeExceptionHandler {

    @Autowired
    EnderecoManager enderecoManager;
/*
    @GetMapping
    public ResponseEntity<List<Endereco>> findAllEndereco() {
        List<Endereco> enderecos = enderecoManager.findAllEndereco();
        for(Endereco endereco : enderecos){
            endereco.removeLinks();
            long id = endereco.getId();
            endereco.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EnderecoController.class).findEnderecoById((Long) id)).withSelfRel());
        }
        return new ResponseEntity<>(enderecos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Endereco> findEnderecoById(@PathVariable("id") Long id){
        Optional<Endereco> endereco = enderecoManager.findById(id);
        endereco.get().removeLinks();
        endereco.get().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EnderecoController.class).findAllEndereco()).withRel("Lista de Endereços"));
        return new ResponseEntity<Endereco>(endereco.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Endereco> saveEndereco(@RequestBody @Valid Endereco endereco){
        Endereco endereco1 = enderecoManager.saveEndereco(endereco);

        endereco1.removeLinks();
        endereco1.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EnderecoController.class).findAllEndereco()).withRel("Lista de Endereços"));
        return new ResponseEntity<Endereco>(endereco1, HttpStatus.CREATED);

    }

    @PutMapping
    public ResponseEntity<Endereco> updateEndereco(@RequestBody @Valid Endereco endereco){
        Endereco endereco1 = (enderecoManager.updateEndereco(endereco));
        endereco1.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EnderecoController.class).findAllEndereco()).withRel("Lista de Endereços"));
        return new ResponseEntity<Endereco>(endereco1, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<Endereco>> deleteEndereco(@PathVariable("id") Long id){
        enderecoManager.deleteEndereco(id);
        return findAllEndereco();
    }
*/
}
