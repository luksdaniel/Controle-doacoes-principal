package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.instituicao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceCreateErrorException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
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
        instituicao.setDataImplantacao(LocalDate.now());
        Instituicao instituicao1 = instituicaoService.saveInstituicao(instituicao);
        if (instituicao1 == null){
            throw new ResourceCreateErrorException("Não foi possível criar instituição");
        }else{
            return instituicao1;
        }
    }

    @Override
    public Instituicao updateInstituicao(Instituicao instituicao) {
        if(instituicaoService.findById(instituicao.getId()).isEmpty()){
            throw new ResourceNotFoundException("Instituição não encontrada");
        }else {
            return (instituicaoService.updateInstituicao(instituicao));
        }
    }

    @Override
    public void deleteInstituicao(Long id) {
        if(instituicaoService.findById(id).isEmpty()){
            throw new ResourceNotFoundException("Instituição não econtrada");
        }else {
            instituicaoService.deleteInstituicao(id);
        }
    }
}
