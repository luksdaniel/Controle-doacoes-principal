package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.service.lembreteDoacao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.LembreteDoacao;

import java.util.List;
import java.util.Optional;

public interface LembreteDoacaoServiceBase {

    List<LembreteDoacao> findAllLembreteDoacao();

    Optional<LembreteDoacao> findById(long id);

    LembreteDoacao saveLembreteDoacao(LembreteDoacao lembreteDoacao);

    LembreteDoacao updateLembreteDoacao(LembreteDoacao lembreteDoacao);

    void deleteLembreteDoacao(long id);

}
