package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.service.ajusteManual;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.AjusteManualEstoque;

import java.util.List;

public interface AjusteManualServiceBase {

    List<AjusteManualEstoque> findAllAjusteManual();

    List<AjusteManualEstoque> findByItemId(long itemId);

    AjusteManualEstoque saveAjusteManual(AjusteManualEstoque ajusteManualEstoque);

    AjusteManualEstoque cancelaAjusteManual(long id);

}
