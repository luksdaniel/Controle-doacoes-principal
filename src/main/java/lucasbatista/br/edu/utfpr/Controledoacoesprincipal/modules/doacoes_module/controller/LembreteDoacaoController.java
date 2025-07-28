package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.controller;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptionHandler.EntityValidateExceptionHandler;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.LembreteDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.service.lembreteDoacao.LembreteDoacaoServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lembrete-doacao")
public class LembreteDoacaoController extends EntityValidateExceptionHandler {

    @Autowired
    LembreteDoacaoServiceBase lembreteDoacaoServiceBase;

    @GetMapping
    public ResponseEntity<List<LembreteDoacao>> findAllLembreteDoacao() {
        List<LembreteDoacao> lembreteList = lembreteDoacaoServiceBase.findAllLembreteDoacao();
        /*for(LembreteDoacao lembrete : lembreteList){
            lembrete.removeLinks();
            long id = lembrete.getId();
            lembrete.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LembreteDoacaoController.class).findLembreteDoacaoById((Long) id)).withSelfRel());
        }*/
        return new ResponseEntity<>(lembreteList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LembreteDoacao> findLembreteDoacaoById(@PathVariable("id") Long id){
        Optional<LembreteDoacao> lembreteDoacao = lembreteDoacaoServiceBase.findById(id);
/*
        lembreteDoacao.get().removeLinks();
        lembreteDoacao.get().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LembreteDoacaoController.class).findAllLembreteDoacao()).withRel("Lista de Lembretes"));]

 */
        return new ResponseEntity<LembreteDoacao>(lembreteDoacao.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LembreteDoacao> saveLembreteDoacao(@RequestBody @Valid LembreteDoacao lembreteDoacao){
        LembreteDoacao lembreteInterno = lembreteDoacaoServiceBase.saveLembreteDoacao(lembreteDoacao);

        /*lembreteInterno.removeLinks();
        lembreteInterno.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LembreteDoacaoController.class).findAllLembreteDoacao()).withRel("Lista de Lembretes"));*/
        return new ResponseEntity<LembreteDoacao>(lembreteInterno, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<LembreteDoacao> updateLembreteDoacao(@RequestBody @Valid LembreteDoacao lembreteDoacao){
        LembreteDoacao lembreteInterno = (lembreteDoacaoServiceBase.updateLembreteDoacao(lembreteDoacao));
        /*lembreteInterno.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LembreteDoacaoController.class).findAllLembreteDoacao()).withRel("Lista de Lembretes"));*/
        return new ResponseEntity<LembreteDoacao>(lembreteInterno, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<LembreteDoacao>> deleteLembreteDoacao(@PathVariable("id") Long id){
        lembreteDoacaoServiceBase.deleteLembreteDoacao(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
