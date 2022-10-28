package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.itemEntregaDoacao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.entregaDoacao.EntregaDoacao;

import java.util.List;

public interface ItemEntregaManager {

    List<ItemEntregaDoacao> saveAllItensEntrega(List<ItemEntregaDoacao> itens);

    ItemEntregaDoacao saveItenEntrega(ItemEntregaDoacao item);

    ItemEntregaDoacao findByIdd(Long id);

    List<ItemEntregaDoacao> updateAllItensEntrega(List<ItemEntregaDoacao> itens, long entregaId);

}
