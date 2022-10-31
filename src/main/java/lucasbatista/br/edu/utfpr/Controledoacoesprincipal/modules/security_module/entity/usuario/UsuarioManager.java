package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.entity.usuario;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.dto.PasswordDto;

import java.util.List;
import java.util.Optional;

public interface UsuarioManager {

    List<Usuario> findAllUsuario();
    Optional<Usuario> findById(Long id);

    Optional<Usuario> findByDoadorId(Long id);
    Optional<Usuario> findByUserName(String username);
    Usuario saveUsuario(Usuario usuario);
    //Usuario updateUsuario(Usuario usuario);
    Usuario updateUsername(String newUsername, long id);

    Usuario updatePassword(PasswordDto passwordDto, long id);
    void deleteUsuario(Long id);

}
