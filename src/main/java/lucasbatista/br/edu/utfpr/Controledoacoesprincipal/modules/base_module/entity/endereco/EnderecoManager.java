package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.endereco;

import java.util.List;
import java.util.Optional;

public interface EnderecoManager {

    List<Endereco> findAllEndereco();
    Optional<Endereco> findById(Long id);
    Endereco saveEndereco(Endereco endereco);
    Endereco updateEndereco(Endereco endereco);
    void deleteEndereco(Long id);

}
