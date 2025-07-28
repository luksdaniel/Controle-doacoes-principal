package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.service.endereco;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceCreateErrorException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.Endereco;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class EnderecoService implements EnderecoServiceBase {

    @Autowired
    EnderecoRepository enderecoRepository;

    @Override
    public List<Endereco> findAllEndereco() {
        List<Endereco> enderecos = enderecoRepository.findAll();
        if(enderecos.isEmpty()){
            throw new ResourceNotFoundException("Endereços não encontrados");
        }else {
            return enderecos;
        }
    }

    @Override
    public Optional<Endereco> findById(Long id) {
        Optional<Endereco> endereco = enderecoRepository.findById(id);
        if(!endereco.isPresent()){
            throw new ResourceNotFoundException("Endereço não encontrado");
        }else {
            return endereco;
        }
    }

    @Override
    public Endereco saveEndereco(Endereco endereco) {
        Endereco endereco1 = enderecoRepository.save(endereco);
        if(endereco1 == null){
            throw new ResourceCreateErrorException("Não foi possível criar o Endereço");
        }else {
            return endereco1;
        }
    }

    @Override
    public Endereco updateEndereco(Endereco endereco) {
        verificaEnderecoJaCadastrado(endereco.getId());
        return (enderecoRepository.save(endereco));
    }

    @Override
    public void deleteEndereco(Long id) {
        verificaEnderecoJaCadastrado(id);
        enderecoRepository.deleteById(id);
    }

    private void verificaEnderecoJaCadastrado(Long id){
        if(!enderecoRepository.findById(id).isPresent())
            throw new ResourceNotFoundException("Endereço não encontrado");
    }
}
