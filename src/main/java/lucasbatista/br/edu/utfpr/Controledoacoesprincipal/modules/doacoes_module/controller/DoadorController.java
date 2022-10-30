package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.controller;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptionHandler.EntityValidateExceptionHandler;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.beneficiario.Beneficiario;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.doador.Doador;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.doador.DoadorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/doador")
public class DoadorController extends EntityValidateExceptionHandler {

    @Autowired
    DoadorManager doadorManager;

    @GetMapping
    public ResponseEntity<List<Doador>> findAllDoador() {
        List<Doador> doadorList = doadorManager.findAllDoador();
        for(Doador doador : doadorList){
            doador.removeLinks();
            long id = doador.getId();
            doador.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DoadorController.class).findDoadorById((Long) id)).withSelfRel());
        }
        return new ResponseEntity<>(doadorList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doador> findDoadorById(@PathVariable("id") Long id){
        Optional<Doador> doador = doadorManager.findById(id);

        doador.get().removeLinks();
        doador.get().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DoadorController.class).findAllDoador()).withRel("Lista de doadores"));
        return new ResponseEntity<Doador>(doador.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Doador> saveDoador(@RequestBody @Valid Doador doador){
        Doador doadorInterno = doadorManager.saveDoador(doador);

        doadorInterno.removeLinks();
        doadorInterno.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DoadorController.class).findAllDoador()).withRel("Lista de doadores"));
        return new ResponseEntity<Doador>(doadorInterno, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Doador> updateDoador(@RequestBody @Valid Doador doador){
        Doador doadorInterno = (doadorManager.updateDoador(doador));
        doadorInterno.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DoadorController.class).findAllDoador()).withRel("Lista de doadores"));
        return new ResponseEntity<Doador>(doadorInterno, HttpStatus.OK);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<Doador> cancelDoador(@PathVariable("id") Long id){
        Doador doador = doadorManager.cancelDoador(id);

        return new ResponseEntity<Doador>(doador, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<Doador>> deleteDoador(@PathVariable("id") Long id){
        doadorManager.deleteDoador(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
