package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.repository;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.LembreteDoacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LembreteDoacaoRepository extends JpaRepository<LembreteDoacao, Long> {
}
