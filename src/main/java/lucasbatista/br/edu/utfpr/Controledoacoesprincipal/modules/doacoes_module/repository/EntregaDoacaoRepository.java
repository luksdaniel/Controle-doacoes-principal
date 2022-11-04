package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.repository;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.coletaDoacao.ColetaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.entregaDoacao.EntregaDoacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EntregaDoacaoRepository extends JpaRepository<EntregaDoacao, Long> {

    List<EntregaDoacao> findByBeneficiarioId(long id);

    List<EntregaDoacao> findByUsuarioRegistroId(long id);

    List<EntregaDoacao> findByUsuarioRegistroIdOrderByDataEntregaDesc(long id);

    List<EntregaDoacao> findByDataEntregaBetween(LocalDate dataInicio, LocalDate dataFim);
}
