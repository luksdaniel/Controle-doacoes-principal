package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.persistence.ajusteManualEstoque;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.ajusteManualEstoque.AjusteManualEstoque;

import java.util.List;
import java.util.Optional;

public interface AjusteManualEstService {

    List<AjusteManualEstoque> findAllAjusteManualEst();

    Optional<AjusteManualEstoque> findById(Long id);

    AjusteManualEstoque saveAjusteManualEstoque(AjusteManualEstoque ajusteManualEstoque);

    AjusteManualEstoque updateAjusteManualEstoque(AjusteManualEstoque ajusteManualEstoque);

    void deleteAjusteManualEstoque(Long id);

    List<AjusteManualEstoque> findByItemId(long id);

}
