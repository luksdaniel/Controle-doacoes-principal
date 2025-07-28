package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.controller;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptionHandler.EntityValidateExceptionHandler;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.ColetaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.service.coletaDoacao.ColetaDoacaoServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/coleta-doacao")
public class ColetaDoacaoController extends EntityValidateExceptionHandler {

    ColetaDoacaoServiceBase coletaDoacaoServiceBase;

    @Autowired
    public ColetaDoacaoController(ColetaDoacaoServiceBase coletaDoacaoServiceBase) {
        this.coletaDoacaoServiceBase = coletaDoacaoServiceBase;
    }

    @GetMapping
    public ResponseEntity<List<ColetaDoacao>> findAllColetaDoacao() {
        List<ColetaDoacao> coletaList = coletaDoacaoServiceBase.findAllColetaDoacao();
        for(ColetaDoacao coleta : coletaList){
            coleta.removeLinks();
            long id = coleta.getId();
        }
        return new ResponseEntity<>(coletaList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ColetaDoacao> findColetaById(@PathVariable("id") Long id){
        Optional<ColetaDoacao> coletaDoacao = coletaDoacaoServiceBase.findById(id);

        return new ResponseEntity<>(coletaDoacao.get(), HttpStatus.OK);
    }

    @GetMapping("doador/{id}")
    public ResponseEntity<List<ColetaDoacao>> findColetaByIdDoador(@PathVariable("id") Long id){
        List<ColetaDoacao> coletaDoacao = coletaDoacaoServiceBase.findByIdDoador(id);

        return new ResponseEntity<>(coletaDoacao, HttpStatus.OK);
    }

    @GetMapping("usuario-registro/username/{username}")
    public ResponseEntity<List<ColetaDoacao>> findColetaByIdusuarioRegistro(@PathVariable("username") String username){
        List<ColetaDoacao> coletaDoacao = coletaDoacaoServiceBase.findByIdUsuarioRegistro(username);

        return new ResponseEntity<>(coletaDoacao, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ColetaDoacao> saveColetaDoacao(@RequestBody @Valid ColetaDoacao coletaDoacao){
        ColetaDoacao coletaDoacaoInterna = coletaDoacaoServiceBase.saveColetaDoacao(coletaDoacao);

        return new ResponseEntity<ColetaDoacao>(coletaDoacaoInterna, HttpStatus.CREATED);
    }

    @PutMapping("effect/{id}/{user-id}")
    public ResponseEntity<ColetaDoacao> effectColetaDoacao(@PathVariable("id") Long id,
                                                           @PathVariable("user-id") Long userId){

        ColetaDoacao coletaDoacaoInterna = coletaDoacaoServiceBase.efetivaColetaDoacao(id, userId);

        return new ResponseEntity<ColetaDoacao>(coletaDoacaoInterna, HttpStatus.OK);
    }

    @PutMapping("cancel")
    public ResponseEntity<ColetaDoacao> cancelColetaDoacao(@RequestBody ColetaDoacao coletaDoacao){

        ColetaDoacao coletaDoacaoInterna = coletaDoacaoServiceBase.cancelaColetaDoacao(coletaDoacao);

        return new ResponseEntity<ColetaDoacao>(coletaDoacaoInterna, HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<ColetaDoacao> updateColetaDoacao(@RequestBody ColetaDoacao coletaDoacao){

        ColetaDoacao coletaDoacaoInterna = coletaDoacaoServiceBase.updateColetaDoacao(coletaDoacao);

        return new ResponseEntity<ColetaDoacao>(coletaDoacaoInterna, HttpStatus.OK);
    }

}
