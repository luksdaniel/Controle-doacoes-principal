package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.controller;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptionHandler.EntityValidateExceptionHandler;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.beneficiario.BeneficiarioManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.beneficiario.Beneficiario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/beneficiario")
public class BeneficiarioController extends EntityValidateExceptionHandler {

    @Autowired
    BeneficiarioManager beneficiarioManager;

    @GetMapping
    public ResponseEntity<List<Beneficiario>> findAllBeneficiario() {
        List<Beneficiario> beneficiarioList = beneficiarioManager.findAllBeneficiario();
        for(Beneficiario beneficiario : beneficiarioList){
            beneficiario.removeLinks();
            long id = beneficiario.getId();
            beneficiario.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BeneficiarioController.class).findBeneficiarioById((Long) id)).withSelfRel());
        }
        return new ResponseEntity<>(beneficiarioList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Beneficiario> findBeneficiarioById(@PathVariable("id") Long id){
        Optional<Beneficiario> beneficiario = beneficiarioManager.findById(id);

        beneficiario.get().removeLinks();
        beneficiario.get().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BeneficiarioController.class).findAllBeneficiario()).withRel("Lista de beneficiarios"));
        return new ResponseEntity<Beneficiario>(beneficiario.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Beneficiario> saveBeneficiario(@RequestBody @Valid Beneficiario beneficiario){
        Beneficiario beneficiarioInterno = beneficiarioManager.saveBeneficiario(beneficiario);

        beneficiarioInterno.removeLinks();
        beneficiarioInterno.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BeneficiarioController.class).findAllBeneficiario()).withRel("Lista de beneficiarios"));
        return new ResponseEntity<Beneficiario>(beneficiarioInterno, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Beneficiario> updateBeneficiario(@RequestBody @Valid Beneficiario beneficiario){
        Beneficiario beneficiarioInterno = (beneficiarioManager.updateBeneficiario(beneficiario));
        beneficiarioInterno.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BeneficiarioController.class).findAllBeneficiario()).withRel("Lista de beneficiarios"));
        return new ResponseEntity<Beneficiario>(beneficiarioInterno, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<Beneficiario>> deleteBeneficiario(@PathVariable("id") Long id){
        beneficiarioManager.deleteBeneficiario(id);
        return findAllBeneficiario();
    }
}
