package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.repository;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.ItemEntregaDoacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemEntregaRepository extends JpaRepository<ItemEntregaDoacao, Long> {

    List<ItemEntregaDoacao> findByEntregaDoacaoId(long id);

    List<ItemEntregaDoacao> findByItemId(long id);

}
