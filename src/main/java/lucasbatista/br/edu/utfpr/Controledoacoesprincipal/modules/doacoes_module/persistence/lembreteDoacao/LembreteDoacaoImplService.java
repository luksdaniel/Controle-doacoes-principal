package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.lembreteDoacao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.LembreteDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.repository.LembreteDoacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LembreteDoacaoImplService implements LembreteDoacaoService{

    @Autowired
    LembreteDoacaoRepository lembreteDoacaoRepository;

    @Override
    public List<LembreteDoacao> findAllDoador() {
        return lembreteDoacaoRepository.findAll();
    }

    @Override
    public Optional<LembreteDoacao> findById(Long id) {
        return lembreteDoacaoRepository.findById(id);
    }

    @Override
    public LembreteDoacao saveLembreteDoacao(LembreteDoacao lembreteDoacao) {
        return lembreteDoacaoRepository.save(lembreteDoacao);
    }

    @Override
    public LembreteDoacao updateLembreteDoacao(LembreteDoacao lembreteDoacao) {
        return lembreteDoacaoRepository.save(lembreteDoacao);
    }

    @Override
    public void deleteLembreteDoacao(Long id) {
        lembreteDoacaoRepository.deleteById(id);
    }
}
