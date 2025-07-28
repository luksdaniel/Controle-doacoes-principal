package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.controller;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptionHandler.EntityValidateExceptionHandler;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.Doador;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.service.doador.DoadorServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/doador")
public class DoadorController extends EntityValidateExceptionHandler {

    @Autowired
    DoadorServiceBase doadorServiceBase;

    @GetMapping
    public ResponseEntity<List<Doador>> findAllDoador() {
        List<Doador> doadorList = doadorServiceBase.findAllDoador();
        for(Doador doador : doadorList){
            doador.removeLinks();
            long id = doador.getId();
            doador.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DoadorController.class).findDoadorById((Long) id)).withSelfRel());
        }
        return new ResponseEntity<>(doadorList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doador> findDoadorById(@PathVariable("id") Long id){
        Optional<Doador> doador = doadorServiceBase.findById(id);

        doador.get().removeLinks();
        doador.get().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DoadorController.class).findAllDoador()).withRel("Lista de doadores"));
        return new ResponseEntity<Doador>(doador.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Doador> saveDoador(@RequestBody @Valid Doador doador){
        Doador doadorInterno = doadorServiceBase.saveDoador(doador);

        doadorInterno.removeLinks();
        doadorInterno.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DoadorController.class).findAllDoador()).withRel("Lista de doadores"));
        return new ResponseEntity<Doador>(doadorInterno, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Doador> updateDoador(@RequestBody @Valid Doador doador){
        Doador doadorInterno = (doadorServiceBase.updateDoador(doador));
        doadorInterno.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DoadorController.class).findAllDoador()).withRel("Lista de doadores"));
        return new ResponseEntity<Doador>(doadorInterno, HttpStatus.OK);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<Doador> cancelDoador(@PathVariable("id") Long id){
        Doador doador = doadorServiceBase.cancelDoador(id);

        return new ResponseEntity<Doador>(doador, HttpStatus.OK);
    }

    @PutMapping("/uncancel/{id}")
    public ResponseEntity<Doador> uncancelDoador(@PathVariable("id") Long id){
        Doador doador = doadorServiceBase.uncancelDoador(id);

        return new ResponseEntity<Doador>(doador, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<Doador>> deleteDoador(@PathVariable("id") Long id){
        doadorServiceBase.deleteDoador(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
