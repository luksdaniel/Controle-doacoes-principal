package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.service.usuario;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.dto.PasswordDto;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioServiceBase {

    List<Usuario> findAllUsuario();
    Optional<Usuario> findById(Long id);
    Optional<Usuario> findByDoadorId(Long id);
    Optional<Usuario> findByUserName(String username);
    Usuario saveUsuario(Usuario usuario);
    Usuario updateUsername(String newUsername, long id);
    Usuario updatePassword(PasswordDto passwordDto, long id);
    void deleteUsuario(Long id);

}
