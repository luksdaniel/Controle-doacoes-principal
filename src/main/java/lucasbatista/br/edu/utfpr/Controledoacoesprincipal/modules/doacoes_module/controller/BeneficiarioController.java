package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.controller;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptionHandler.EntityValidateExceptionHandler;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.service.beneficiario.BeneficiarioServiceBase;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.Beneficiario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/beneficiario")
public class BeneficiarioController extends EntityValidateExceptionHandler {

    BeneficiarioServiceBase beneficiarioManager;

    @Autowired
    public BeneficiarioController(BeneficiarioServiceBase beneficiarioManager) {
        this.beneficiarioManager = beneficiarioManager;
    }

    @GetMapping
    public ResponseEntity<List<Beneficiario>> findAllBeneficiario() {
        List<Beneficiario> beneficiarioList = beneficiarioManager.findAllBeneficiario();

        return new ResponseEntity<>(beneficiarioList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Beneficiario> findBeneficiarioById(@PathVariable("id") Long id){
        Optional<Beneficiario> beneficiario = beneficiarioManager.findById(id);

        return new ResponseEntity<Beneficiario>(beneficiario.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Beneficiario> saveBeneficiario(@RequestBody @Valid Beneficiario beneficiario){
        Beneficiario beneficiarioInterno = beneficiarioManager.saveBeneficiario(beneficiario);

        return new ResponseEntity<Beneficiario>(beneficiarioInterno, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Beneficiario> updateBeneficiario(@RequestBody @Valid Beneficiario beneficiario){
        Beneficiario beneficiarioInterno = (beneficiarioManager.updateBeneficiario(beneficiario));

        return new ResponseEntity<Beneficiario>(beneficiarioInterno, HttpStatus.OK);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<Beneficiario> cancelBeneficiario(@PathVariable("id") Long id){
        Beneficiario beneficiario = beneficiarioManager.cancelBeneficiario(id);

        return new ResponseEntity<Beneficiario>(beneficiario, HttpStatus.OK);
    }

    @PutMapping("/uncancel/{id}")
    public ResponseEntity<Beneficiario> uncancelBeneficiario(@PathVariable("id") Long id){
        Beneficiario beneficiario = beneficiarioManager.uncancelBeneficiario(id);

        return new ResponseEntity<Beneficiario>(beneficiario, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<Beneficiario>> deleteBeneficiario(@PathVariable("id") Long id){
        beneficiarioManager.deleteBeneficiario(id);
        return findAllBeneficiario();
    }
}
