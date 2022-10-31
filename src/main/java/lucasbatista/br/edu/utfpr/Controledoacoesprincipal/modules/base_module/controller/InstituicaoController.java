package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.controller;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptionHandler.EntityValidateExceptionHandler;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.instituicao.InstituicaoManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.instituicao.Instituicao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/instituicao")
public class InstituicaoController extends EntityValidateExceptionHandler {
    
    @Autowired
    InstituicaoManager instituicaoManager;

    @GetMapping("")
    public ResponseEntity<Instituicao> findInstituicao(){
        Optional<Instituicao> instituicao = instituicaoManager.find();

        return new ResponseEntity<Instituicao>(instituicao.get(), HttpStatus.OK);

    }

    @PutMapping
    public ResponseEntity<Instituicao> updateInstituicao(@RequestBody @Valid Instituicao instituicao){
        Instituicao instituicaoInterno = (instituicaoManager.updateInstituicao(instituicao));

        return new ResponseEntity<Instituicao>(instituicaoInterno, HttpStatus.OK);
    }

}
