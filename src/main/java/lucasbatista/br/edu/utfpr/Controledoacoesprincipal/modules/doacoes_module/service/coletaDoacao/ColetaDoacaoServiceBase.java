package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.service.coletaDoacao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.ColetaDoacao;

import java.util.List;
import java.util.Optional;

public interface ColetaDoacaoServiceBase {

    List<ColetaDoacao> findAllColetaDoacao();

    List<ColetaDoacao> findByIdDoador(Long id);

    List<ColetaDoacao> findByIdUsuarioRegistro(String username);

    Optional<ColetaDoacao> findById(Long id);

    ColetaDoacao saveColetaDoacao(ColetaDoacao coletaDoacao);

    ColetaDoacao updateColetaDoacao(ColetaDoacao coletaDoacao);

    ColetaDoacao cancelaColetaDoacao(ColetaDoacao coletaDoacao);

    ColetaDoacao efetivaColetaDoacao(Long id, Long usuarioId);

}
