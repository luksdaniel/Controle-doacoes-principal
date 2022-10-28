package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.lembreteDoacao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.lembreteDoacao.LembreteDoacao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LembreteDoacaoService {

    List<LembreteDoacao> findAllLembreteDoacao();

    Optional<LembreteDoacao> findById(Long id);

    Optional<LembreteDoacao> findLembretePendente(LocalDate data);

    LembreteDoacao saveLembreteDoacao(LembreteDoacao lembreteDoacao);

    LembreteDoacao updateLembreteDoacao(LembreteDoacao lembreteDoacao);

    void deleteLembreteDoacao(Long id);

}
