package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.repository;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.unidadeMedida.UnidadeMedida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadeMedidaRepository extends JpaRepository<UnidadeMedida, Long> {
}
