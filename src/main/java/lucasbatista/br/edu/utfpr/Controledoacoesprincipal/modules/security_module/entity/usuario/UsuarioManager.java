package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.entity.usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioManager {

    List<Usuario> findAllUsuario();
    Optional<Usuario> findById(Long id);
    Usuario saveUsuario(Usuario usuario);
    Usuario updateUsuario(Usuario usuario);
    void deleteUsuario(Long id);

}
