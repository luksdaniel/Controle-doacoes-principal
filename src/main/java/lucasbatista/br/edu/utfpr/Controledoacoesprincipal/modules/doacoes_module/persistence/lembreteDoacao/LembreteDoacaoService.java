package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.lembreteDoacao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.LembreteDoacao;

import java.util.List;
import java.util.Optional;

public interface LembreteDoacaoService {

    List<LembreteDoacao> findAllDoador();

    Optional<LembreteDoacao> findById(Long id);

    LembreteDoacao saveLembreteDoacao(LembreteDoacao lembreteDoacao);

    LembreteDoacao updateLembreteDoacao(LembreteDoacao lembreteDoacao);

    void deleteLembreteDoacao(Long id);

}