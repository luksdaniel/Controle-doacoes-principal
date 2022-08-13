package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.repository;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.entity.AvisoFaltaItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvisoFaltaItemRepository extends JpaRepository<AvisoFaltaItem, Long> {

}
