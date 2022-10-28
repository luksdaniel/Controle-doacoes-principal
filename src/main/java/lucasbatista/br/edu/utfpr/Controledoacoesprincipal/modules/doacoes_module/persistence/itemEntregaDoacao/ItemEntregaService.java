package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.itemEntregaDoacao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.itemEntregaDoacao.ItemEntregaDoacao;

import java.util.List;
import java.util.Optional;

public interface ItemEntregaService {

    List<ItemEntregaDoacao> findByEntregaId(long id);

    Optional<ItemEntregaDoacao> findById(Long id);

    ItemEntregaDoacao saveItemEntrega(ItemEntregaDoacao item);

    List<ItemEntregaDoacao> saveAllItemEntrega(List<ItemEntregaDoacao> itemEntregaList);

    ItemEntregaDoacao updateItemEntrega(ItemEntregaDoacao itemEntregaDoacao);

    void deleteItemEntregaDoacao(Long id);

}
