package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.persistence.unidadeMedida;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.entity.UnidadeMedida;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.repository.UnidadeMedidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnidadeMedidaImplService implements UnidadeMedidaService {

    @Autowired
    UnidadeMedidaRepository unidadeMedidaRepository;

    @Override
    public List<UnidadeMedida> findAllUnidadeMedida() {
        return unidadeMedidaRepository.findAll();
    }

    @Override
    public Optional<UnidadeMedida> findById(Long id) {
        return unidadeMedidaRepository.findById(id);
    }

    @Override
    public UnidadeMedida saveUnidadeMedida(UnidadeMedida unidadeMedida) {
        return unidadeMedidaRepository.save(unidadeMedida);
    }

    @Override
    public UnidadeMedida updaUnidadeMedida(UnidadeMedida unidadeMedida) {
        return unidadeMedidaRepository.save(unidadeMedida);
    }

    @Override
    public void deleteUnidadeMedida(Long id) {
        unidadeMedidaRepository.deleteById(id);
    }
}
