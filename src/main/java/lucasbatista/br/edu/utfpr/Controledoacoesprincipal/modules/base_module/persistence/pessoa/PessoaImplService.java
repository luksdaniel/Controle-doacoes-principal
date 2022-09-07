package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.persistence.pessoa;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.pessoa.Pessoa;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaImplService implements PessoaService{

    @Autowired
    PessoaRepository pessoaRepository;

    @Override
    public List<Pessoa> findAllPessoa() {
        return pessoaRepository.findAll();
    }

    @Override
    public Optional<Pessoa> findById(Long id) {
        return pessoaRepository.findById(id);
    }

    @Override
    public Pessoa savePessoa(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    @Override
    public Pessoa updatePessoa(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    @Override
    public void deletePessoa(Long id) {
        pessoaRepository.deleteById(id);
    }
}
