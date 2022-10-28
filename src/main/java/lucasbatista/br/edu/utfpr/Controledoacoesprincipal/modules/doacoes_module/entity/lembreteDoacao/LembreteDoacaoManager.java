package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.lembreteDoacao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.ajusteManualEstoque.AjusteManualEstoque;

import java.util.List;
import java.util.Optional;

public interface LembreteDoacaoManager {

    List<LembreteDoacao> findAllLembreteDoacao();

    Optional<LembreteDoacao> findById(long id);

    LembreteDoacao saveLembreteDoacao(LembreteDoacao lembreteDoacao);

    LembreteDoacao updateLembreteDoacao(LembreteDoacao lembreteDoacao);

    void deleteLembreteDoacao(long id);

}
