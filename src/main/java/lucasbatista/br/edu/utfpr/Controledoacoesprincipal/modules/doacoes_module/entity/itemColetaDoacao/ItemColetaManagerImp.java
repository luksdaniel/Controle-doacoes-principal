package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.itemColetaDoacao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.itemColetaDoacao.ItemColetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class ItemColetaManagerImp implements ItemColetaManager{

    @Autowired
    ItemColetaService itemColetaService;

    @Override
    public List<ItemColetaDoacao> saveAllItensColeta(List<ItemColetaDoacao> itens) {
        return itemColetaService.saveItemAllColetaDoacao(itens);
    }
}
