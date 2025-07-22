package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.pessoa;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.Enumerators.TipoPessoa;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceCreateErrorException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceIntegrityException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.endereco.Endereco;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.endereco.EnderecoManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.persistence.pessoa.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class PessoaManagerImp implements PessoaManager{

    @Autowired
    PessoaService pessoaService;

    @Autowired
    EnderecoManager enderecoManager;

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
        if(!pessoa.isPresent()){
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
        if (!pessoaService.findById(id).isPresent())
            throw new ResourceNotFoundException("Pessoa não encontrada");
    }

    @Override
    public void setaAtributosIniciais(Pessoa pessoa){

        pessoa.setEstaCancelado(false);
        pessoa.setDataCadastro(LocalDate.now());

    }

    @Override
    public void validaCPFeCNPJ(Pessoa pessoa){
/*
        if(pessoa.getTipoPessoa().equals(TipoPessoa.PESSOA_JURIDICA)) {
            if (pessoa.getCnpj() == null || pessoa.getCnpj().isEmpty())
                throw new ResourceIntegrityException("Foi informada Pessoa Jurídica mas o CNPJ está vazio");
            pessoa.setCpf(null);
        }else if (pessoa.getTipoPessoa().equals(TipoPessoa.PESSOA_FISICA)){
            if(pessoa.getCpf() == null || pessoa.getCpf().isEmpty())
                throw new ResourceIntegrityException("Foi informada Pessoa Física mas o CPF está vazio");
            pessoa.setCnpj(null);
        }*/
    }

    @Override
    public void criaEnderecoPessoa(Pessoa pessoa){

        Endereco endereco = enderecoManager.saveEndereco(pessoa.getEndereco());
        pessoa.setEndereco(endereco);

    }
}
