package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.service.doador;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.*;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.Instituicao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.repository.InstituicaoRepository;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.service.endereco.EnderecoServiceBase;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.service.pessoa.PessoaServiceBase;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.Doador;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.repository.DoadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class DoadorService implements DoadorServiceBase {

    DoadorRepository doadorRepository;
    PessoaServiceBase pessoaManager;
    EnderecoServiceBase enderecoManager;
    InstituicaoRepository instituicaoRepository;

    @Autowired
    public DoadorService(DoadorRepository doadorRepository, PessoaServiceBase pessoaManager, EnderecoServiceBase enderecoManager, InstituicaoRepository instituicaoRepository) {
        this.doadorRepository = doadorRepository;
        this.pessoaManager = pessoaManager;
        this.enderecoManager = enderecoManager;
        this.instituicaoRepository = instituicaoRepository;
    }

    @Override
    public List<Doador> findAllDoador() {
        List<Doador> doadorList = doadorRepository.findAll();
        if(doadorList.isEmpty()){
            throw new ResourceNotFoundException("Doadores não encontrados");
        }else{
            return doadorList;
        }
    }

    @Override
    public List<Doador> retornaDoadoresQueRecebemEmails() {
        return doadorRepository.findDoadorsByRecebeEmailsTrue();
    }

    @Override
    public Optional<Doador> findById(Long id) {
        Optional<Doador> doador = doadorRepository.findById(id);
        if(!doador.isPresent()){
            throw new ResourceNotFoundException("Doador não encontrado");
        }else{
            return doador;
        }
    }

    @Override
    public Doador saveDoador(Doador doador) {
        Optional<Doador> entity = doadorRepository.findById(doador.getId());

        if (entity.isPresent()) return updateDoador(doador);

        pessoaManager.setaAtributosIniciais(doador);
        pessoaManager.criaEnderecoPessoa(doador);
        validaEnderecoExistente(doador);
        pessoaManager.validaCPFeCNPJ(doador);

        validaEmailDuplicado(doador);
        return doadorRepository.save(doador);
    }

    @Override
    public Doador cancelDoador(Long id) {
        Optional<Doador> doador = findById(id);

        if (doador.get().isEstaCancelado())
            throw new BusinessException("Doador já cancelado");

        doador.get().setEstaCancelado(true);

        return doadorRepository.save(doador.get());
    }

    @Override
    public Doador uncancelDoador(Long id) {
        Optional<Doador> doador = findById(id);

        if (!doador.get().isEstaCancelado())
            throw new BusinessException("Doador não está cancelado");

        doador.get().setEstaCancelado(false);

        return doadorRepository.save(doador.get());
    }

    @Override
    public Doador updateDoador(Doador doador) {
        validaEnderecoExistente(doador);

        pessoaManager.validaCPFeCNPJ(doador);
        doador.setDataCadastro(LocalDate.now());

        verificaDoadorJaCadastrado(doador.getId());
        validaEmailDuplicado(doador);
        return (doadorRepository.save(doador));
    }

    @Override
    public void deleteDoador(Long id) {
        verificaDoadorJaCadastrado(id);
        doadorRepository.deleteById(id);
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
        if(!doadorRepository.findById(id).isPresent())
            throw new ResourceNotFoundException("Doador não encontrado");
    }

    private void validaEmailDuplicado(Doador doador){
        Optional<Doador> doador1 = doadorRepository.findByEmail(doador.getEmail());
        Optional<Instituicao> instituicao = instituicaoRepository.findByEmail(doador.getEmail());
        if ((doador1.isPresent() && doador.getId() != doador1.get().getId()) ||
                instituicao.isPresent())
            throw new ResourceIntegrityException("Já existe um registro gravado com o e-mail fornecido!");

    }
}
