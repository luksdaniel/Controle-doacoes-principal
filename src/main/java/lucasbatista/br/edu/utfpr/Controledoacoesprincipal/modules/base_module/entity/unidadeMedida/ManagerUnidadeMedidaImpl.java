package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.unidadeMedida;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.persistence.unidadeMedida.UnidadeMedidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ManagerUnidadeMedidaImpl implements ManagerUnidadeMedida{

    @Autowired
    UnidadeMedidaService unidadeMedidaService;

    @Override
    public List<UnidadeMedida> findAllUnidadeMedida() {
        return unidadeMedidaService.findAllUnidadeMedida();
    }

    @Override
    public Optional<UnidadeMedida> findById(Long id) {
        return unidadeMedidaService.findById(id);
    }

    @Override
    public UnidadeMedida saveUnidadeMedida(UnidadeMedida unidadeMedida) {
        return unidadeMedidaService.saveUnidadeMedida(unidadeMedida);
    }

    @Override
    public UnidadeMedida updaUnidadeMedida(UnidadeMedida unidadeMedida) {
        return unidadeMedidaService.updaUnidadeMedida(unidadeMedida);
    }

    @Override
    public void deleteUnidadeMedida(Long id) {
        unidadeMedidaService.deleteUnidadeMedida(id);
    }
}
