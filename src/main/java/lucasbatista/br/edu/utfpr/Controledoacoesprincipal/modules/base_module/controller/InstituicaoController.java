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

    @GetMapping
    public ResponseEntity<List<Instituicao>> findAllInstituicao() {
        List<Instituicao> instituicaoList = instituicaoManager.findAllInstituicao();
        for(Instituicao instituicao : instituicaoList){
            instituicao.removeLinks();
            long id = instituicao.getId();
            instituicao.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InstituicaoController.class).findInstituicaoById((Long) id)).withSelfRel());
        }
        return new ResponseEntity<>(instituicaoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instituicao> findInstituicaoById(@PathVariable("id") Long id){
        Optional<Instituicao> instituicao = instituicaoManager.findById(id);

        instituicao.get().removeLinks();
        instituicao.get().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InstituicaoController.class).findAllInstituicao()).withRel("Lista de Instituicões"));
        return new ResponseEntity<Instituicao>(instituicao.get(), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Instituicao> saveInstituicao(@RequestBody @Valid Instituicao instituicao){
        Instituicao instituicaoInterno = instituicaoManager.saveInstituicao(instituicao);
        instituicaoInterno.removeLinks();
        instituicaoInterno.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InstituicaoController.class).findAllInstituicao()).withRel("Lista de Instituicões"));
        return new ResponseEntity<Instituicao>(instituicaoInterno, HttpStatus.CREATED);

    }

    @PutMapping
    public ResponseEntity<Instituicao> updateInstituicao(@RequestBody @Valid Instituicao instituicao){
        Instituicao instituicaoInterno = (instituicaoManager.updateInstituicao(instituicao));
        instituicaoInterno.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InstituicaoController.class).findAllInstituicao()).withRel("Lista de Instituicões"));
        return new ResponseEntity<Instituicao>(instituicaoInterno, HttpStatus.OK);
    }

    /* Comentado por não haver a possibilidade de exluir a instituicao na aplicação final
    @DeleteMapping("/{id}")
    public ResponseEntity<List<Instituicao>> deleteInstituicao(@PathVariable("id") Long id){
        instituicaoManager.deleteInstituicao(id);
        return findAllInstituicao();
    }
    */
}
