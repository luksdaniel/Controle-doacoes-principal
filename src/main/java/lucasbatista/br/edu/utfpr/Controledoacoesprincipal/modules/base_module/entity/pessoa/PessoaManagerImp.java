package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.pessoa;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceCreateErrorException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.persistence.pessoa.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PessoaManagerImp implements PessoaManager{

    @Autowired
    PessoaService pessoaService;

    @Override
    public List<Pessoa> findAllPessoa() {
        List<Pessoa> pessoaList = pessoaService.findAllPessoa();
        if(pessoaList.isEmpty()){
            throw new ResourceNotFoundException("Pessoas não encontradas");
        }else{
            return pessoaList;
        }
    }

    @Override
    public Optional<Pessoa> findById(Long id) {
        Optional<Pessoa> pessoa = pessoaService.findById(id);
        if(pessoa.isEmpty()){
            throw new ResourceNotFoundException("Pessoa não encontrada");
        }else {
            return pessoa;
        }
    }

    @Override
    public Pessoa savePessoa(Pessoa pessoa) {
        Pessoa pessoa1 = pessoaService.savePessoa(pessoa);
        if(pessoa1 == null){
            throw new ResourceCreateErrorException("Não foi possível cadastrar a pessoa");
        }else {
            return pessoa1;
        }
    }

    @Override
    public Pessoa updatePessoa(Pessoa pessoa) {
        verificaPessoaJaCadastrada(pessoa.getId());
        Pessoa pessoa1 = (pessoaService.updatePessoa(pessoa));
        return pessoa1;
    }

    @Override
    public void deletePessoa(Long id) {
        verificaPessoaJaCadastrada(id);
        pessoaService.deletePessoa(id);
    }

    private void verificaPessoaJaCadastrada(Long id){
        if (pessoaService.findById(id).isEmpty())
            throw new ResourceNotFoundException("Pessoa não encontrada");
    }
}
