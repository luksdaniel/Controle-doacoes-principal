package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.persistence.usuario;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.entity.Endereco;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<Usuario> findAllUsuario();

    Optional<Usuario> findById(Long id);

    Usuario saveUsuario(Usuario usuario);

    Usuario updateUsuario(Usuario usuario);

    void deleteUsuario(Long id);


}
