package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.doador;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.Enumerators.TipoPessoa;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.DependencyNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceCreateErrorException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceIntegrityException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.endereco.Endereco;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.endereco.EnderecoManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.doador.DoadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class DoadorManagerImp implements DoadorManager{

    @Autowired
    DoadorService doadorService;

    @Autowired
    EnderecoManager enderecoManager;

    @Override
    public List<Doador> findAllDoador() {
        List<Doador> doadorList = doadorService.findAllDoador();
        if(doadorList.isEmpty()){
            throw new ResourceNotFoundException("Doadores não encontrados");
        }else{
            return doadorList;
        }
    }

    @Override
    public Optional<Doador> findById(Long id) {
        Optional<Doador> doador = doadorService.findById(id);
        if(doador.isEmpty()){
            throw new ResourceNotFoundException("Doador não encontrado");
        }else{
            return doador;
        }
    }

    @Override
    public Doador saveDoador(Doador doador) {
        setaAtributosIniciais(doador);
        criaEnderecoDoador(doador);
        validaEnderecoExistente(doador);
        validaCPFeCNPJ(doador);

        Doador doadorInterno = doadorService.saveDoador(doador);
        if(doadorInterno == null){
            throw new ResourceCreateErrorException("Não foi possível criar o doador");
        }else{
            return doadorInterno;
        }
    }

    @Override
    public Doador updateDoador(Doador doador) {
        validaEnderecoExistente(doador);
        validaCPFeCNPJ(doador);

        verificaDoadorJaCadastrado(doador.getId());
        return (doadorService.updateDoador(doador));
    }

    @Override
    public void deleteDoador(Long id) {
        verificaDoadorJaCadastrado(id);
        doadorService.deleteDoador(id);
    }

    private void validaEnderecoExistente(Doador doador){
        if (enderecoManager.findById(doador.getEndereco().getId()).isEmpty())
            throw new DependencyNotFoundException("Não foi localizado o endereço para criação do doador");
    }

    private void setaAtributosIniciais(Doador doador){

        doador.setEstaCancelao(false);
        doador.setDataCadastro(LocalDate.now());

    }

    private void validaCPFeCNPJ(Doador doador){

        if(doador.getTipoPessoa().equals(TipoPessoa.PESSOA_JURIDICA) &&
                doador.getCnpj().isEmpty()){
            throw new ResourceIntegrityException("O doador foi informado como pessoa Jurídica mas seu CNPJ está vazio");
        }else if (doador.getTipoPessoa().equals(TipoPessoa.PESSOA_FISICA) &&
                doador.getCpf().isEmpty()){
            throw new ResourceIntegrityException("O doador foi informado como pessoa Física mas seu CPF está vazio");
        }

    }

    private void criaEnderecoDoador(Doador doador){

      Endereco endereco = enderecoManager.saveEndereco(doador.getEndereco());
      doador.setEndereco(endereco);

    }

    private void verificaDoadorJaCadastrado(Long id){
        if(doadorService.findById(id).isEmpty())
            throw new ResourceNotFoundException("Doador não encontrado");
    }
}
