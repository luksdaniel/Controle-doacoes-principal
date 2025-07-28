package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.controller;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptionHandler.EntityValidateExceptionHandler;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.EntregaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.service.entregaDoacao.EntregaDoacaoServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/entrega-doacao")
public class EntregaDoacaoController extends EntityValidateExceptionHandler {

    EntregaDoacaoServiceBase entregaDoacaoServiceBase;

    @Autowired
    public EntregaDoacaoController(EntregaDoacaoServiceBase entregaDoacaoServiceBase) {
        this.entregaDoacaoServiceBase = entregaDoacaoServiceBase;
    }

    @GetMapping
    public ResponseEntity<List<EntregaDoacao>> findAllEntregaDoacao() {
        List<EntregaDoacao> entregaList = entregaDoacaoServiceBase.findAllEntregaDoacao();

        return new ResponseEntity<>(entregaList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntregaDoacao> findEntregaById(@PathVariable("id") Long id){
        Optional<EntregaDoacao> entregaDoacao = entregaDoacaoServiceBase.findById(id);

        return new ResponseEntity<>(entregaDoacao.get(), HttpStatus.OK);
    }

    @GetMapping("beneficiario/{id}")
    public ResponseEntity<List<EntregaDoacao>> findEntregaByIdbeneficiario(@PathVariable("id") Long id){
        List<EntregaDoacao> entregaDoacaoList = entregaDoacaoServiceBase.findByIdBeneficiario(id);

        return new ResponseEntity<>(entregaDoacaoList, HttpStatus.OK);
    }

    @GetMapping("usuario-registro/{id}")
    public ResponseEntity<List<EntregaDoacao>> findEntregaByIdusuarioRegistro(@PathVariable("id") Long id){
        List<EntregaDoacao> entregaDoacaoList = entregaDoacaoServiceBase.findByIdUsuarioRegistro(id);

        return new ResponseEntity<>(entregaDoacaoList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EntregaDoacao> saveEntregaDoacao(@RequestBody @Valid EntregaDoacao entregaDoacao){
        EntregaDoacao entregaDoacao1 = entregaDoacaoServiceBase.saveEntregaDoacao(entregaDoacao);

        return new ResponseEntity<EntregaDoacao>(entregaDoacao1, HttpStatus.CREATED);
    }

    @PutMapping("cancel")
    public ResponseEntity<EntregaDoacao> cancelEntregaDoacao(@RequestBody EntregaDoacao entregaDoacao){
        EntregaDoacao entregaInterna = entregaDoacaoServiceBase.cancelaEntregaDoacao(entregaDoacao);

        return new ResponseEntity<EntregaDoacao>(entregaInterna, HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<EntregaDoacao> updateEntregaDoacao(@RequestBody EntregaDoacao entregaDoacao){
        EntregaDoacao entregaInterna = entregaDoacaoServiceBase.updateEntregaDoacao(entregaDoacao);

        return new ResponseEntity<EntregaDoacao>(entregaInterna, HttpStatus.OK);
    }

}
