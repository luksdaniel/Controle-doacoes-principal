package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.controller;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.doador.Doador;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.doador.DoadorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/doador")
public class DoadorController {

    @Autowired
    DoadorManager doadorManager;

    @GetMapping
    public ResponseEntity<List<Doador>> findAllDoador() {
        List<Doador> doadorList = doadorManager.findAllDoador();
        if(doadorList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            for(Doador doador : doadorList){
                doador.removeLinks();
                long id = doador.getId();
                doador.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DoadorController.class).findDoadorById((Long) id)).withSelfRel());
            }
            return new ResponseEntity<>(doadorList, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doador> findDoadorById(@PathVariable("id") Long id){
        Optional<Doador> doador = doadorManager.findById(id);
        if(doador.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            doador.get().removeLinks();
            doador.get().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DoadorController.class).findAllDoador()).withRel("Lista de doadores"));
            return new ResponseEntity<Doador>(doador.get(), HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<Doador> saveDoador(@RequestBody Doador doador){
        Doador doadorInterno = doadorManager.saveDoador(doador);
        if(doador == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else{
            doadorInterno.removeLinks();
            doadorInterno.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DoadorController.class).findAllDoador()).withRel("Lista de doadores"));
            return new ResponseEntity<Doador>(doadorInterno, HttpStatus.OK);
        }

    }

    @PutMapping
    public ResponseEntity<Doador> updateDoador(@RequestBody Doador doador){
        if(doadorManager.findById(doador.getId()).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Doador doadorInterno = (doadorManager.updateDoador(doador));
            doadorInterno.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DoadorController.class).findAllDoador()).withRel("Lista de doadores"));
            return new ResponseEntity<Doador>(doadorInterno, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<Doador>> deleteDoador(@PathVariable("id") Long id){
        try{
            doadorManager.deleteDoador(id);
            return findAllDoador();
        }catch (Exception ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

}
