package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.itemColetaDoacao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.itemColetaDoacao.ItemColetaDoacao;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ItemColetaService {

    List<ItemColetaDoacao> findAllItemColetaDoacao();

    Optional<ItemColetaDoacao> findById(Long id);

    List<ItemColetaDoacao> saveItemAllColetaDoacao(List<ItemColetaDoacao> itemColetaDoacao);

    ItemColetaDoacao updateItemColetaDoacao(ItemColetaDoacao itemColetaDoacao);

    void deleteItemColetaDoacao(Long id);

}
