package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.service.itemEntregaDoacao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.ItemEntregaDoacao;

import java.util.List;

public interface ItemEntregaServiceBase {

    List<ItemEntregaDoacao> saveAllItensEntrega(List<ItemEntregaDoacao> itens);

    ItemEntregaDoacao saveItenEntrega(ItemEntregaDoacao item);

    ItemEntregaDoacao findByIdd(Long id);

    List<ItemEntregaDoacao> updateAllItensEntrega(List<ItemEntregaDoacao> itens, long entregaId);

}
