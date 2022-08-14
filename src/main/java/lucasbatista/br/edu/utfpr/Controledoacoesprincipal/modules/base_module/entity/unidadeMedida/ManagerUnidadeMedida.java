package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.unidadeMedida;

import java.util.List;
import java.util.Optional;

public interface ManagerUnidadeMedida {

    List<UnidadeMedida> findAllUnidadeMedida();

    Optional<UnidadeMedida> findById(Long id);

    UnidadeMedida saveUnidadeMedida(UnidadeMedida unidadeMedida);

    UnidadeMedida updaUnidadeMedida(UnidadeMedida unidadeMedida);

    void deleteUnidadeMedida(Long id);

}
