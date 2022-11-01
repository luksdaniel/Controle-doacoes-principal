package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.entregaDoacao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.coletaDoacao.ColetaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.entregaDoacao.EntregaDoacao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EntregaDoacaoService {

    List<EntregaDoacao> findAllEntregaDoacao();

    Optional<EntregaDoacao> findById(Long id);

    List<EntregaDoacao> findByBeneficiarioId(Long id);

    List<EntregaDoacao> findByUsuarioRegistroId(Long id);

    List<EntregaDoacao> findByDataBetween(LocalDate dataInicio, LocalDate dataFim);

    EntregaDoacao retornaUltimaEntregaBeneficiario(long id);

    EntregaDoacao saveEntregaDoacao(EntregaDoacao entregaDoacao);

    EntregaDoacao updateEntregaDoacao(EntregaDoacao entregaDoacao);

    void deleteEntregaDoacao(Long id);

}
