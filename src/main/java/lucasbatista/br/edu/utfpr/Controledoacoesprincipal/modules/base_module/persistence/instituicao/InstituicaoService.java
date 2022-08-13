package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.persistence.instituicao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.Instituicao;

import java.util.List;
import java.util.Optional;

public interface InstituicaoService {

    List<Instituicao> findAllInstituicao();

    Optional<Instituicao> findById(Long id);

    Instituicao saveEndereco(Instituicao instituicao);

    Instituicao updateEndereco(Instituicao instituicao);

    void deleteInstituicao(Long id);

}