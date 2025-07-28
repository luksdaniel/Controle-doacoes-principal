package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.service.entregaDoacao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.EntregaDoacao;

import java.util.List;
import java.util.Optional;

public interface EntregaDoacaoServiceBase {

    List<EntregaDoacao> findAllEntregaDoacao();

    List<EntregaDoacao> findByIdBeneficiario(Long id);

    List<EntregaDoacao> findByIdUsuarioRegistro(Long id);

    Optional<EntregaDoacao> findById(Long id);

    EntregaDoacao saveEntregaDoacao(EntregaDoacao entregaDoacao);

    EntregaDoacao updateEntregaDoacao(EntregaDoacao entregaDoacao);

    EntregaDoacao cancelaEntregaDoacao(EntregaDoacao entregaDoacao);

}
