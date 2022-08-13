package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.persistence.endereco;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.entity.Endereco;

import java.util.List;
import java.util.Optional;

public interface EnderecoService {

    List<Endereco> findAllEndereco();

    Optional<Endereco> findById(Long id);

    Endereco saveEndereco(Endereco endereco);

    Endereco updateEndereco(Endereco endereco);

    void deleteEndereco(Long id);

}
