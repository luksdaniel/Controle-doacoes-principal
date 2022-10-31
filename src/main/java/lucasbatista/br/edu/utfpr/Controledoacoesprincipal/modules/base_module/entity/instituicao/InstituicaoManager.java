package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.instituicao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.pessoa.Pessoa;

import java.util.List;
import java.util.Optional;

public interface InstituicaoManager {

    //Instituicao findAllInstituicao();
    Optional<Instituicao> find();
    Optional<Instituicao> findById(Long id);
    Instituicao saveInstituicao(Instituicao instituicao);
    Instituicao updateInstituicao(Instituicao instituicao);
    void deleteInstituicao(Long id);

}
