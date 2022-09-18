package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.itemColetaDoacao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.itemColetaDoacao.ItemColetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ItemColetaManagerImp implements ItemColetaManager{

    @Autowired
    ItemColetaService itemColetaService;

    @Override
    public List<ItemColetaDoacao> saveAllItensColeta(List<ItemColetaDoacao> itens) {
        for (ItemColetaDoacao itemAtual: itens) {
            if (itemAtual.getDataInclusao() == null)
                itemAtual.setDataInclusao(LocalDate.now());
        }

        return itemColetaService.saveItemAllColetaDoacao(itens);
    }
}
