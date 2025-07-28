package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.service.itemColetaDoacao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.ItemColetaDoacao;

import java.util.List;

public interface ItemColetaServiceBase {

    List<ItemColetaDoacao> saveAllItensColeta(List<ItemColetaDoacao> itens);

    ItemColetaDoacao saveItenColeta(ItemColetaDoacao item);

    ItemColetaDoacao findByIdd(Long id);

    List<ItemColetaDoacao> updateAllItensColeta(List<ItemColetaDoacao> itens, long coletaId);

}
