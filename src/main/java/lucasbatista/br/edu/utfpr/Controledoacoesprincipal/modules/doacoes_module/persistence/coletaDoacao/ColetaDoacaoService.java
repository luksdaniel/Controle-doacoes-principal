package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.coletaDoacao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.coletaDoacao.ColetaDoacao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ColetaDoacaoService {

    List<ColetaDoacao> findAllColetaDoacao();

    List<ColetaDoacao> findByIdDoador(long id);

    List<ColetaDoacao> findByIdUsuarioRegistro(long id);

    List<ColetaDoacao> findByDataBetween(LocalDate dataInicio, LocalDate dataFim);

    Optional<ColetaDoacao> findById(Long id);

    ColetaDoacao saveColetaDoacao(ColetaDoacao coletaDoacao);

    ColetaDoacao updateColetaDoacao(ColetaDoacao coletaDoacao);

    void deleteColetaDoacao(Long id);

}
