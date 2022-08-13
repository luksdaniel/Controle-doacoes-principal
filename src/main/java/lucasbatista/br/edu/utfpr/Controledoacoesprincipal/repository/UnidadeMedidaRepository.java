package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.repository;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.entity.UnidadeMedida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadeMedidaRepository extends JpaRepository<UnidadeMedida, Long> {
}
