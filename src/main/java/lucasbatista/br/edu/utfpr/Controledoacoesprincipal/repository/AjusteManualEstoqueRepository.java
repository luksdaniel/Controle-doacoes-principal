package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.repository;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.entity.AjusteManualEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AjusteManualEstoqueRepository extends JpaRepository<AjusteManualEstoque, Long> {
}
