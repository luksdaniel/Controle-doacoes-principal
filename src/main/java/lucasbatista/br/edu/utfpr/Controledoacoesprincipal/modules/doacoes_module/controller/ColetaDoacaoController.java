package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.controller;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptionHandler.EntityValidateExceptionHandler;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.coletaDoacao.ColetaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.coletaDoacao.ColetaDoacaoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("coleta-doacao/")
public class ColetaDoacaoController extends EntityValidateExceptionHandler {

    @Autowired
    ColetaDoacaoManager coletaDoacaoManager;

    @GetMapping
    public ResponseEntity<List<ColetaDoacao>> findAllColetaDoacao() {
        List<ColetaDoacao> coletaList = coletaDoacaoManager.findAllColetaDoacao();
        for(ColetaDoacao coleta : coletaList){
            coleta.removeLinks();
            long id = coleta.getId();
        //  coleta.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ColetaDoacaoController.class).findColetaById((Long) id)).withSelfRel());
        }
        return new ResponseEntity<>(coletaList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ColetaDoacao> findColetaById(@PathVariable("id") Long id){
        Optional<ColetaDoacao> coletaDoacao = coletaDoacaoManager.findById(id);

        //coletaDoacao.get().removeLinks();
        //coletaDoacao.get().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ColetaDoacaoController.class).findAllColetaDoacao()).withRel("Lista de Coletas"));
        return new ResponseEntity<>(coletaDoacao.get(), HttpStatus.OK);
    }

    @GetMapping("doador/{id}")
    public ResponseEntity<List<ColetaDoacao>> findColetaByIdDoador(@PathVariable("id") Long id){
        List<ColetaDoacao> coletaDoacao = coletaDoacaoManager.findByIdDoador(id);

        //coletaDoacao.get().removeLinks();
        //coletaDoacao.get().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ColetaDoacaoController.class).findAllColetaDoacao()).withRel("Lista de Coletas"));
        return new ResponseEntity<>(coletaDoacao, HttpStatus.OK);
    }

    @GetMapping("usuario-registro/{id}")
    public ResponseEntity<List<ColetaDoacao>> findColetaByIdusuarioRegistro(@PathVariable("id") Long id){
        List<ColetaDoacao> coletaDoacao = coletaDoacaoManager.findByIdUsuarioRegistro(id);

        //coletaDoacao.get().removeLinks();
        //coletaDoacao.get().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ColetaDoacaoController.class).findAllColetaDoacao()).withRel("Lista de Coletas"));
        return new ResponseEntity<>(coletaDoacao, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ColetaDoacao> saveColetaDoacao(@RequestBody @Valid ColetaDoacao coletaDoacao){
        ColetaDoacao coletaDoacaoInterna = coletaDoacaoManager.saveColetaDoacao(coletaDoacao);

        //coletaDoacaoInterna.removeLinks();
        //coletaDoacaoInterna.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DoadorController.class).findAllDoador()).withRel("Lista de doadores"));
        return new ResponseEntity<ColetaDoacao>(coletaDoacaoInterna, HttpStatus.CREATED);
    }

    @PutMapping("effect/{id}/{user-id}")
    public ResponseEntity<ColetaDoacao> effectColetaDoacao(@PathVariable("id") Long id,
                                                           @PathVariable("user-id") Long userId){

        ColetaDoacao coletaDoacaoInterna = coletaDoacaoManager.efetivaColetaDoacao(id, userId);

        //coletaDoacaoInterna.removeLinks();
        //coletaDoacaoInterna.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DoadorController.class).findAllDoador()).withRel("Lista de doadores"));
        return new ResponseEntity<ColetaDoacao>(coletaDoacaoInterna, HttpStatus.OK);
    }

    @PutMapping("cancel")
    public ResponseEntity<ColetaDoacao> effectColetaDoacao(@RequestBody ColetaDoacao coletaDoacao){

        ColetaDoacao coletaDoacaoInterna = coletaDoacaoManager.cancelaColetaDoacao(coletaDoacao);

        //coletaDoacaoInterna.removeLinks();
        //coletaDoacaoInterna.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DoadorController.class).findAllDoador()).withRel("Lista de doadores"));
        return new ResponseEntity<ColetaDoacao>(coletaDoacaoInterna, HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<ColetaDoacao> updateColetaDoacao(@RequestBody ColetaDoacao coletaDoacao){

        ColetaDoacao coletaDoacaoInterna = coletaDoacaoManager.updateColetaDoacao(coletaDoacao);

        //coletaDoacaoInterna.removeLinks();
        //coletaDoacaoInterna.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DoadorController.class).findAllDoador()).withRel("Lista de doadores"));
        return new ResponseEntity<ColetaDoacao>(coletaDoacaoInterna, HttpStatus.OK);
    }

}
