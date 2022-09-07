package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.persistence.usuario;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.usuario.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<Usuario> findAllUsuario();

    Optional<Usuario> findById(Long id);

    Usuario saveUsuario(Usuario usuario);

    Usuario updateUsuario(Usuario usuario);

    void deleteUsuario(Long id);


}
