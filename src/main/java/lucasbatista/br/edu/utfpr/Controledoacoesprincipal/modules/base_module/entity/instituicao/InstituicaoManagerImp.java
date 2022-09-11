package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.instituicao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.DependencyNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceCreateErrorException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.endereco.EnderecoManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.pessoa.Pessoa;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.pessoa.PessoaManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.persistence.instituicao.InstituicaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class InstituicaoManagerImp implements InstituicaoManager{

    @Autowired
    InstituicaoService instituicaoService;
    @Autowired
    PessoaManager pessoaManager;
    @Autowired
    EnderecoManager enderecoManager;

    @Override
    public List<Instituicao> findAllInstituicao() {
        List<Instituicao> instituicaoList = instituicaoService.findAllInstituicao();
        if(instituicaoList.isEmpty()){
            throw new ResourceNotFoundException("Instiuições não encontradas");
        }else {
            return instituicaoList;
        }
    }

    @Override
    public Optional<Instituicao> findById(Long id) {
        Optional<Instituicao> instituicao = instituicaoService.findById(id);
        if (instituicao.isEmpty()){
            throw new ResourceNotFoundException("Instituição não encontrada");
        }else {
            return instituicao;
        }
    }

    @Override
    public Instituicao saveInstituicao(Instituicao instituicao) {
        pessoaManager.setaAtributosIniciais(instituicao);
        pessoaManager.criaEnderecoPessoa(instituicao);
        validaEnderecoExistente(instituicao);
        pessoaManager.validaCPFeCNPJ(instituicao);

        Instituicao instituicao1 = instituicaoService.saveInstituicao(instituicao);
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

        pessoaManager.validaCPFeCNPJ(instituicao);
        pessoaManager.setaAtributosIniciais(instituicao);

        verificaInstituicaoJaCadastrada(instituicao.getId());
        return (instituicaoService.updateInstituicao(instituicao));
    }

    @Override
    public void deleteInstituicao(Long id) {
        verificaInstituicaoJaCadastrada(id);
        instituicaoService.deleteInstituicao(id);
    }

    public void atualizaEnderecoComId(Instituicao instituicao){
        Optional<Instituicao> optionalInstituicao = findById(instituicao.getId());
        Instituicao instituicaoInterna = optionalInstituicao.get();
        instituicao.setEndereco(instituicaoInterna.getEndereco());
    }

    public void validaEnderecoExistente(Instituicao instituicao){
        if (enderecoManager.findById(instituicao.getEndereco().getId()).isEmpty())
            throw new DependencyNotFoundException("Não foi localizado o endereço para criação da instituição");
    }

    private void verificaInstituicaoJaCadastrada(Long id){
        if(instituicaoService.findById(id).isEmpty())
            throw new ResourceNotFoundException("Instituição não econtrada");
    }
}
