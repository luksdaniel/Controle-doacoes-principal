package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.persistence.usuario;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.entity.usuario.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<Usuario> findAllUsuario();

    Optional<Usuario> findById(Long id);

    Optional<Usuario> findByDoadorId(Long id);

    Optional<Usuario> findByUserName(String username);

    Usuario saveUsuario(Usuario usuario);

    Usuario updateUsuario(Usuario usuario);

    void deleteUsuario(Long id);


}
