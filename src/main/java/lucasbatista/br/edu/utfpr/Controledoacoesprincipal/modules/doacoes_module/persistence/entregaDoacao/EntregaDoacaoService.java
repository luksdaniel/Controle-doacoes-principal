package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.entregaDoacao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.EntregaDoacao;

import java.util.List;
import java.util.Optional;

public interface EntregaDoacaoService {

    List<EntregaDoacao> findAllEntregaDoacao();

    Optional<EntregaDoacao> findById(Long id);

    EntregaDoacao saveEntregaDoacao(EntregaDoacao entregaDoacao);

    EntregaDoacao updateEntregaDoacao(EntregaDoacao entregaDoacao);

    void deleteEntregaDoacao(Long id);

}
