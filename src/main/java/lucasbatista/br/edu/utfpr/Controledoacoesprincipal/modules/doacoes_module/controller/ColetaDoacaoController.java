package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.controller;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptionHandler.EntityValidadeExceptionHandler;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.coletaDoacao.ColetaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.coletaDoacao.ColetaDoacaoManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.doador.Doador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("coleta-doacao/")
public class ColetaDoacaoController extends EntityValidadeExceptionHandler {

    @Autowired
    ColetaDoacaoManager coletaDoacaoManager;

    @PostMapping
    public ResponseEntity<ColetaDoacao> saveColetaDoacao(@RequestBody @Valid ColetaDoacao coletaDoacao){
        ColetaDoacao coletaDoacaoInterna = coletaDoacaoManager.saveColetaDoacao(coletaDoacao);

        //coletaDoacaoInterna.removeLinks();
        //coletaDoacaoInterna.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DoadorController.class).findAllDoador()).withRel("Lista de doadores"));
        return new ResponseEntity<ColetaDoacao>(coletaDoacaoInterna, HttpStatus.CREATED);
    }

}
