package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.service.pessoa;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.Pessoa;

import java.util.List;
import java.util.Optional;

public interface PessoaServiceBase {

    List<Pessoa> findAllPessoa();
    Optional<Pessoa> findById(Long id);
    Pessoa savePessoa(Pessoa pessoa);
    Pessoa updatePessoa(Pessoa pessoa);
    void deletePessoa(Long id);
    public void setaAtributosIniciais(Pessoa pessoa);
    public void validaCPFeCNPJ(Pessoa pessoa);
    public void criaEnderecoPessoa(Pessoa pessoa);

}
