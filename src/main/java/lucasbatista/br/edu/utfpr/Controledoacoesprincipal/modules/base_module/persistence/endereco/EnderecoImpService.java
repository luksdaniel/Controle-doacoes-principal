package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.persistence.endereco;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.endereco.Endereco;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoImpService implements EnderecoService{

    @Autowired
    EnderecoRepository enderecoRepository;

    @Override
    public List<Endereco> findAllEndereco() {
        return enderecoRepository.findAll();
    }

    @Override
    public Optional<Endereco> findById(Long id) {
        return enderecoRepository.findById(id);
    }

    @Override
    public Endereco saveEndereco(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    @Override
    public Endereco updateEndereco(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    @Override
    public void deleteEndereco(Long id) {
        enderecoRepository.deleteById(id);
    }
}
