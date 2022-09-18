package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.coletaDoacao;

import java.util.List;
import java.util.Optional;

public interface ColetaDoacaoManager {

    List<ColetaDoacao> findAllColetaDoacao();

    List<ColetaDoacao> findByIdDoador(Long id);

    List<ColetaDoacao> findByIdUsuarioLancamento(Long id);

    Optional<ColetaDoacao> findById(Long id);

    ColetaDoacao saveColetaDoacao(ColetaDoacao coletaDoacao);

    ColetaDoacao updateColetaDoacao(ColetaDoacao coletaDoacao);

    ColetaDoacao cancelaColetaDoacao(ColetaDoacao coletaDoacao);

    ColetaDoacao EfetivaColetaDoacao(ColetaDoacao coletaDoacao);

}
