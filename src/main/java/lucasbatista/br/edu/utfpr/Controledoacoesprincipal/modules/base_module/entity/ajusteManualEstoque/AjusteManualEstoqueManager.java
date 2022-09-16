package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.ajusteManualEstoque;

import java.util.List;

public interface AjusteManualEstoqueManager {

    List<AjusteManualEstoque> findAllAjusteManual();

    List<AjusteManualEstoque> findByItemId(long itemId);

    AjusteManualEstoque saveAjusteManual(AjusteManualEstoque ajusteManualEstoque);

    AjusteManualEstoque cancelaAjusteManual(long id);

}
