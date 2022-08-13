package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.repository;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
