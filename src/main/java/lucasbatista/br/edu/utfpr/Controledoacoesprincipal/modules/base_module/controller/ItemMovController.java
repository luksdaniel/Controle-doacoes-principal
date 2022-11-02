package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.controller;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.dto.MovItemDto;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.item.Item;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.persistence.dadosRelatorio.DadosRelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item-mov")
public class ItemMovController {

    @Autowired
    DadosRelatorioService dadosRelatorioService;

    @GetMapping("/{id}")
    public ResponseEntity<List<MovItemDto>> findMovByItemId(@PathVariable("id") Long id) {
        List<MovItemDto> itemList = dadosRelatorioService.findAllMovimentacoes(id);

        return new ResponseEntity<>(itemList, HttpStatus.OK);
    }

}
