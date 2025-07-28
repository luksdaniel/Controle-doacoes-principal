package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.controller;

import jakarta.validation.Valid;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptionHandler.EntityValidateExceptionHandler;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.AjusteManualEstoque;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.service.ajusteManual.AjusteManualServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ajuste-manual-estoque")
public class AjusteManualEstoqueController extends EntityValidateExceptionHandler {

    AjusteManualServiceBase ajusteManualServiceBase;

    @Autowired
    public AjusteManualEstoqueController(AjusteManualServiceBase ajusteManualServiceBase) {
        this.ajusteManualServiceBase = ajusteManualServiceBase;
    }

    @GetMapping("item/{Id}")
    public ResponseEntity<List<AjusteManualEstoque>> findUsuarioByItemId(@PathVariable("Id") Long id){
        List<AjusteManualEstoque> ajusteManualEstoque = ajusteManualServiceBase.findByItemId(id);

        return new ResponseEntity<>(ajusteManualEstoque, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<AjusteManualEstoque> saveAjusteManual(@RequestBody @Valid AjusteManualEstoque ajusteManualEstoque){
        AjusteManualEstoque ajusteManualInterno = ajusteManualServiceBase.saveAjusteManual(ajusteManualEstoque);
        ajusteManualInterno.removeLinks();

        return new ResponseEntity<>(ajusteManualInterno, HttpStatus.CREATED);
    }

    @PutMapping("cancel/{id}")
    public ResponseEntity<AjusteManualEstoque> cancelaAjusteManual(@PathVariable("id") Long id){
        AjusteManualEstoque ajusteManualInterno = ajusteManualServiceBase.cancelaAjusteManual(id);

        return new ResponseEntity<>(ajusteManualInterno, HttpStatus.OK);
    }

}
