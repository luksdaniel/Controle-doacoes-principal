package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.persistence.pessoa;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.pessoa.Pessoa;

import java.util.List;
import java.util.Optional;

public interface PessoaService {

    List<Pessoa> findAllPessoa();

    Optional<Pessoa> findById(Long id);

    Pessoa savePessoa(Pessoa pessoa);

    Pessoa updatePessoa(Pessoa pessoa);

    void deletePessoa(Long id);

}
