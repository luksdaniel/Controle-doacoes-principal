package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.instituicao;

import java.util.List;
import java.util.Optional;

public interface InstituicaoManager {

    List<Instituicao> findAllInstituicao();
    Optional<Instituicao> findById(Long id);
    Instituicao saveInstituicao(Instituicao instituicao);
    Instituicao updateInstituicao(Instituicao instituicao);
    void deleteInstituicao(Long id);

}
