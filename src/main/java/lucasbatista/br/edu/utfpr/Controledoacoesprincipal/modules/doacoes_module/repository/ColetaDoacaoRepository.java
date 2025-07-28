package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.repository;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.ColetaDoacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ColetaDoacaoRepository extends JpaRepository<ColetaDoacao, Long> {

    List<ColetaDoacao> findColetaDoacaoByDoadorId(long id);

    List<ColetaDoacao> findColetaDoacaoByUsuarioRegistroId(long id);

    List<ColetaDoacao> findByDataEfetivacaoBetween(LocalDate dataInicio, LocalDate dataFim);

}
