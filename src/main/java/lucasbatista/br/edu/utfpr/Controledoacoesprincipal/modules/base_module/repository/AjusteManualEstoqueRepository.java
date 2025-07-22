package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.repository;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.AjusteManualEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AjusteManualEstoqueRepository extends JpaRepository<AjusteManualEstoque, Long> {

    List<AjusteManualEstoque> findByItemId(long id);

}
