package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.doador;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.BusinessException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.DependencyNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceCreateErrorException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.endereco.EnderecoManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.pessoa.PessoaManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.doador.DoadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class DoadorManagerImp implements DoadorManager{

    @Autowired
    DoadorService doadorService;
    @Autowired
    PessoaManager pessoaManager;
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
    public List<Doador> retornaDoadoresQueRecebemEmails() {
        return doadorService.findDoadorsByRecebeEmailsTrue();
    }

    @Override
    public Optional<Doador> findById(Long id) {
        Optional<Doador> doador = doadorService.findById(id);
        if(!doador.isPresent()){
            throw new ResourceNotFoundException("Doador não encontrado");
        }else{
            return doador;
        }
    }

    @Override
    public Doador saveDoador(Doador doador) {
        Optional<Doador> entity = doadorService.findById(doador.getId());

        if (entity.isPresent()) return updateDoador(doador);

        pessoaManager.setaAtributosIniciais(doador);
        pessoaManager.criaEnderecoPessoa(doador);
        validaEnderecoExistente(doador);
        pessoaManager.validaCPFeCNPJ(doador);

        Doador doadorInterno = doadorService.saveDoador(doador);
        if(doadorInterno == null){
            throw new ResourceCreateErrorException("Não foi possível criar o doador");
        }else{
            return doadorInterno;
        }
    }

    @Override
    public Doador cancelDoador(Long id) {
        Optional<Doador> doador = findById(id);

        if (doador.get().isEstaCancelado())
            throw new BusinessException("Doador já cancelado");

        doador.get().setEstaCancelado(true);

        return doadorService.updateDoador(doador.get());
    }

    @Override
    public Doador uncancelDoador(Long id) {
        Optional<Doador> doador = findById(id);

        if (!doador.get().isEstaCancelado())
            throw new BusinessException("Doador não está cancelado");

        doador.get().setEstaCancelado(false);

        return doadorService.updateDoador(doador.get());
    }

    @Override
    public Doador updateDoador(Doador doador) {
        //atualizaEnderecoComId(doador);
        validaEnderecoExistente(doador);

        pessoaManager.validaCPFeCNPJ(doador);
        //pessoaManager.setaAtributosIniciais(doador);
        doador.setDataCadastro(LocalDate.now());

        verificaDoadorJaCadastrado(doador.getId());
        return (doadorService.updateDoador(doador));
    }

    @Override
    public void deleteDoador(Long id) {
        verificaDoadorJaCadastrado(id);
        doadorService.deleteDoador(id);
    }

    public void atualizaEnderecoComId(Doador doador){
        Optional<Doador> optionalDoador = findById(doador.getId());
        Doador doadorInterno = optionalDoador.get();
        doador.setEndereco(doadorInterno.getEndereco());
    }

    public void validaEnderecoExistente(Doador doador){
        if (!enderecoManager.findById(doador.getEndereco().getId()).isPresent())
            throw new DependencyNotFoundException("Não foi localizado o endereço para criação do doador");
    }

    private void verificaDoadorJaCadastrado(Long id){
        if(!doadorService.findById(id).isPresent())
            throw new ResourceNotFoundException("Doador não encontrado");
    }
}
