package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.service.instituicao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.Instituicao;

import java.util.Optional;

public interface InstituicaoServiceBase {

    Optional<Instituicao> find();
    Optional<Instituicao> findById(Long id);
    Instituicao saveInstituicao(Instituicao instituicao);
    Instituicao updateInstituicao(Instituicao instituicao);
    void deleteInstituicao(Long id);

}
