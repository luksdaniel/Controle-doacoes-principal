package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.persistence.instituicao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceIntegrityException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.instituicao.Instituicao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.repository.InstituicaoRepository;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.doador.Doador;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.repository.DoadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstituicaoImplService implements InstituicaoService{

    @Autowired
    InstituicaoRepository instituicaoRepository;

    @Autowired
    DoadorRepository doadorRepository;

    @Override
    public List<Instituicao> findAllInstituicao() {
        return instituicaoRepository.findAll();
    }

    @Override
    public Optional<Instituicao> findById(Long id) {
        return instituicaoRepository.findById(id);
    }

    @Override
    public Optional<Instituicao> findByEmail(String email) {
        return instituicaoRepository.findByEmail(email);
    }

    @Override
    public Instituicao saveInstituicao(Instituicao instituicao) {
        validaEmailDuplicado(instituicao);
        return instituicaoRepository.save(instituicao);
    }

    @Override
    public Instituicao updateInstituicao(Instituicao instituicao) {
        validaEmailDuplicado(instituicao);
        return instituicaoRepository.save(instituicao);
    }

    @Override
    public void deleteInstituicao(Long id) {
        instituicaoRepository.deleteById(id);
    }

    private void validaEmailDuplicado(Instituicao instituicao){
        Optional<Doador> doador1 = doadorRepository.findByEmail(instituicao.getEmail());
        Optional<Instituicao> instituicao1 = instituicaoRepository.findByEmail(instituicao.getEmail());
        if (!doador1.isEmpty() || !instituicao1.isEmpty())
            throw new ResourceIntegrityException("Já existe um registro gravado com o e-mail fornecido!");

    }
}
