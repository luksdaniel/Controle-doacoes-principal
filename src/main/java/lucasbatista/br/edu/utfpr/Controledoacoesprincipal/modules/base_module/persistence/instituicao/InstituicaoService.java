package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.persistence.instituicao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.instituicao.Instituicao;

import java.util.List;
import java.util.Optional;

public interface InstituicaoService {

    List<Instituicao> findAllInstituicao();

    Optional<Instituicao> findById(Long id);

    Optional<Instituicao> findByEmail(String email);

    Instituicao saveInstituicao(Instituicao instituicao);

    Instituicao updateInstituicao(Instituicao instituicao);

    void deleteInstituicao(Long id);

}
