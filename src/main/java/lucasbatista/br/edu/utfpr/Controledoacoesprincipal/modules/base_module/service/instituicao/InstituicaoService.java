package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.service.instituicao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.DependencyNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceCreateErrorException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceIntegrityException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.Instituicao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.service.endereco.EnderecoServiceBase;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.service.pessoa.PessoaServiceBase;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.repository.InstituicaoRepository;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.Doador;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.repository.DoadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class InstituicaoService implements InstituicaoServiceBase {

    InstituicaoRepository instituicaoRepository;
    PessoaServiceBase pessoaManager;
    EnderecoServiceBase enderecoManager;
    DoadorRepository doadorRepository;

    @Autowired
    public InstituicaoService(
            InstituicaoRepository instituicaoRepository,
            PessoaServiceBase pessoaManager,
            EnderecoServiceBase enderecoManager,
            DoadorRepository doadorRepository) {
        this.instituicaoRepository = instituicaoRepository;
        this.pessoaManager = pessoaManager;
        this.enderecoManager = enderecoManager;
        this.doadorRepository = doadorRepository;
    }

    @Override
    public Optional<Instituicao> findById(Long id) {
        Optional<Instituicao> instituicao = instituicaoRepository.findById(id);
        if (!instituicao.isPresent()){
            throw new ResourceNotFoundException("Instituição não encontrada");
        }else {
            return instituicao;
        }
    }

    @Override
    public Optional<Instituicao> find() {
        List<Instituicao> instituicao = instituicaoRepository.findAll();

        if (instituicao.isEmpty()){
            throw new ResourceNotFoundException("Instituição não encontrada");
        }else {
            return Optional.ofNullable(instituicao.get(0));
        }
    }

    @Override
    public Instituicao saveInstituicao(Instituicao instituicao) {
        pessoaManager.setaAtributosIniciais(instituicao);
        pessoaManager.criaEnderecoPessoa(instituicao);
        validaEnderecoExistente(instituicao);
        pessoaManager.validaCPFeCNPJ(instituicao);
        validaEmailDuplicado(instituicao);

        Instituicao instituicao1 = instituicaoRepository.save(instituicao);
        if (instituicao1 == null){
            throw new ResourceCreateErrorException("Não foi possível criar instituição");
        }else{
            return instituicao1;
        }
    }

    @Override
    public Instituicao updateInstituicao(Instituicao instituicao) {
        atualizaEnderecoComId(instituicao);
        validaEnderecoExistente(instituicao);
        validaEmailDuplicado(instituicao);

        pessoaManager.validaCPFeCNPJ(instituicao);
        pessoaManager.setaAtributosIniciais(instituicao);

        verificaInstituicaoJaCadastrada(instituicao.getId());
        return (instituicaoRepository.save(instituicao));
    }

    @Override
    public void deleteInstituicao(Long id) {
        verificaInstituicaoJaCadastrada(id);
        instituicaoRepository.deleteById(id);
    }

    public void atualizaEnderecoComId(Instituicao instituicao){
        Optional<Instituicao> optionalInstituicao = findById(instituicao.getId());
        Instituicao instituicaoInterna = optionalInstituicao.get();
        instituicao.setEndereco(instituicaoInterna.getEndereco());
    }

    public void validaEnderecoExistente(Instituicao instituicao){
        if (!enderecoManager.findById(instituicao.getEndereco().getId()).isPresent())
            throw new DependencyNotFoundException("Não foi localizado o endereço para criação da instituição");
    }

    private void verificaInstituicaoJaCadastrada(Long id){
        if(!instituicaoRepository.findById(id).isPresent())
            throw new ResourceNotFoundException("Instituição não econtrada");
    }

    private void validaEmailDuplicado(Instituicao instituicao){
        Optional<Doador> doador1 = doadorRepository.findByEmail(instituicao.getEmail());
        Optional<Instituicao> instituicao1 = instituicaoRepository.findByEmail(instituicao.getEmail());
        if (doador1.isPresent() || instituicao1.isPresent())
            throw new ResourceIntegrityException("Já existe um registro gravado com o e-mail fornecido!");

    }
}
