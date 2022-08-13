package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.persistence.unidadeMedida;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.entity.UnidadeMedida;

import java.util.List;
import java.util.Optional;

public interface UnidadeMedidaService {

    List<UnidadeMedida> findAllUnidadeMedida();

    Optional<UnidadeMedida> findById(Long id);

    UnidadeMedida saveUnidadeMedida(UnidadeMedida unidadeMedida);

    UnidadeMedida updaUnidadeMedida(UnidadeMedida unidadeMedida);

    void deleteUnidadeMedida(Long id);

}
