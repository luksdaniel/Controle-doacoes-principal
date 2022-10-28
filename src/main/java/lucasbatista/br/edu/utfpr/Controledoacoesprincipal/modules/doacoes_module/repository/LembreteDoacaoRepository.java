package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.repository;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.lembreteDoacao.LembreteDoacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface LembreteDoacaoRepository extends JpaRepository<LembreteDoacao, Long> {

    Optional<LembreteDoacao> findByDataAgendamentoAfterAndAndRepetirTodoMesTrue(LocalDate date);

}
