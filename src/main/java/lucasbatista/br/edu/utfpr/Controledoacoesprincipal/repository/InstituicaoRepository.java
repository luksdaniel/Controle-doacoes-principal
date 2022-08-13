package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.repository;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.entity.Instituicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstituicaoRepository extends JpaRepository<Instituicao, Long> {
}
