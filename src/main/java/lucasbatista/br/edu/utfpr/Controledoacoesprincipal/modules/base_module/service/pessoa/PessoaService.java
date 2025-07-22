package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.service.pessoa;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.Pessoa;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.Endereco;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.service.endereco.EnderecoServiceBase;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class PessoaService implements PessoaServiceBase {

    PessoaRepository pessoaRepository;
    EnderecoServiceBase enderecoManager;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository, EnderecoServiceBase enderecoManager) {
        this.pessoaRepository = pessoaRepository;
        this.enderecoManager = enderecoManager;
    }

    @Override
    public List<Pessoa> findAllPessoa() {
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        if(pessoaList.isEmpty()){
            throw new ResourceNotFoundException("Pessoas não encontradas");
        }else{
            return pessoaList;
        }
    }

    @Override
    public Optional<Pessoa> findById(Long id) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        if(!pessoa.isPresent()){
            throw new ResourceNotFoundException("Pessoa não encontrada");
        }else {
            return pessoa;
        }
    }

    @Override
    public Pessoa savePessoa(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    @Override
    public Pessoa updatePessoa(Pessoa pessoa) {
        verificaPessoaJaCadastrada(pessoa.getId());
        return (pessoaRepository.save(pessoa));
    }

    @Override
    public void deletePessoa(Long id) {
        verificaPessoaJaCadastrada(id);
        pessoaRepository.deleteById(id);
    }

    private void verificaPessoaJaCadastrada(Long id){
        if (!pessoaRepository.findById(id).isPresent())
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
