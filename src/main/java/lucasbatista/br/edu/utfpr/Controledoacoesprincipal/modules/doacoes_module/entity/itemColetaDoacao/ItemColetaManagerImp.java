package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.itemColetaDoacao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.itemColetaDoacao.ItemColetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    @Override
    public ItemColetaDoacao findByIdd(Long id) {
        Optional<ItemColetaDoacao> item = itemColetaService.findById(id);
        if(item.isEmpty()){
            throw new ResourceNotFoundException("Item não encontrado na doação");
        }else{
            return item.get();
        }
    }

    @Override
    public List<ItemColetaDoacao> updateAllItensColeta(List<ItemColetaDoacao> itens) {
        for (ItemColetaDoacao itemAtual: itens) {
            if (itemAtual.getDataInclusao() == null)
                itemAtual.setDataInclusao(LocalDate.now());
            itemColetaService.updateItemColetaDoacao(itemAtual);
        }

        return itens;
    }
}
