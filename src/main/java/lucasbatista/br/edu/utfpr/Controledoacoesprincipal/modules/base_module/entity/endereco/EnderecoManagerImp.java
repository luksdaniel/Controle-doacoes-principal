package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.endereco;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceCreateErrorException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.persistence.endereco.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class EnderecoManagerImp implements EnderecoManager{

    @Autowired
    EnderecoService enderecoService;

    @Override
    public List<Endereco> findAllEndereco() {
        List<Endereco> enderecos = enderecoService.findAllEndereco();
        if(enderecos.isEmpty()){
            throw new ResourceNotFoundException("Endereços não encontrados");
        }else {
            return enderecos;
        }
    }

    @Override
    public Optional<Endereco> findById(Long id) {
        Optional<Endereco> endereco = enderecoService.findById(id);
        if(!endereco.isPresent()){
            throw new ResourceNotFoundException("Endereço não encontrado");
        }else {
            return endereco;
        }
    }

    @Override
    public Endereco saveEndereco(Endereco endereco) {
        Endereco endereco1 = enderecoService.saveEndereco(endereco);
        if(endereco1 == null){
            throw new ResourceCreateErrorException("Não foi possível criar o Endereço");
        }else {
            return endereco1;
        }
    }

    @Override
    public Endereco updateEndereco(Endereco endereco) {
        verificaEnderecoJaCadastrado(endereco.getId());
        return (enderecoService.updateEndereco(endereco));
    }

    @Override
    public void deleteEndereco(Long id) {
        verificaEnderecoJaCadastrado(id);
        enderecoService.deleteEndereco(id);
    }

    private void verificaEnderecoJaCadastrado(Long id){
        if(!enderecoService.findById(id).isPresent())
            throw new ResourceNotFoundException("Endereço não encontrado");
    }
}
