package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.persistence.endereco;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.endereco.Endereco;

import java.util.List;
import java.util.Optional;

public interface EnderecoService {

    List<Endereco> findAllEndereco();

    Optional<Endereco> findById(Long id);

    Endereco saveEndereco(Endereco endereco);

    Endereco updateEndereco(Endereco endereco);

    void deleteEndereco(Long id);

}
