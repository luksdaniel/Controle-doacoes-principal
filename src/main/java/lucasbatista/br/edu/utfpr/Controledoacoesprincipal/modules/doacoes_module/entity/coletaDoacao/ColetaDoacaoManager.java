package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.coletaDoacao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.usuario.Usuario;

import java.util.List;
import java.util.Optional;

public interface ColetaDoacaoManager {

    List<ColetaDoacao> findAllColetaDoacao();

    List<ColetaDoacao> findByIdDoador(Long id);

    List<ColetaDoacao> findByIdUsuarioRegistro(Long id);

    Optional<ColetaDoacao> findById(Long id);

    ColetaDoacao saveColetaDoacao(ColetaDoacao coletaDoacao);

    ColetaDoacao updateColetaDoacao(ColetaDoacao coletaDoacao);

    ColetaDoacao cancelaColetaDoacao(ColetaDoacao coletaDoacao);

    ColetaDoacao EfetivaColetaDoacao(Long id, Long usuarioId);

}
