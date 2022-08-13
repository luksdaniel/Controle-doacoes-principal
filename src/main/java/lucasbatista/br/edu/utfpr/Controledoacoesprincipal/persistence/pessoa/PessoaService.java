package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.persistence.pessoa;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.entity.Pessoa;

import java.util.List;
import java.util.Optional;

public interface PessoaService {

    List<Pessoa> findAllPessoa();

    Optional<Pessoa> findById(Long id);

    Pessoa savePessoa(Pessoa pessoa);

    Pessoa updatePessoa(Pessoa pessoa);

    void deletePessoa(Long id);

}
