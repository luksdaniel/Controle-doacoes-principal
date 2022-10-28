package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.itemColetaDoacao;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ItemColetaManager {

    List<ItemColetaDoacao> saveAllItensColeta(List<ItemColetaDoacao> itens);

    ItemColetaDoacao saveItenColeta(ItemColetaDoacao item);

    ItemColetaDoacao findByIdd(Long id);

    List<ItemColetaDoacao> updateAllItensColeta(List<ItemColetaDoacao> itens, long coletaId);

}
