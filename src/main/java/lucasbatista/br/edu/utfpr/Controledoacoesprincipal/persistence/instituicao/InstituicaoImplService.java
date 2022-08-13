package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.persistence.instituicao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.entity.Instituicao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.repository.InstituicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstituicaoImplService implements InstituicaoService{

    @Autowired
    InstituicaoRepository instituicaoRepository;

    @Override
    public List<Instituicao> findAllInstituicao() {
        return instituicaoRepository.findAll();
    }

    @Override
    public Optional<Instituicao> findById(Long id) {
        return instituicaoRepository.findById(id);
    }

    @Override
    public Instituicao saveEndereco(Instituicao instituicao) {
        return instituicaoRepository.save(instituicao);
    }

    @Override
    public Instituicao updateEndereco(Instituicao instituicao) {
        return instituicaoRepository.save(instituicao);
    }

    @Override
    public void deleteInstituicao(Long id) {
        instituicaoRepository.deleteById(id);
    }
}
