package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.persistence;

import lombok.RequiredArgsConstructor;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.entity.usuario.Usuario;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.persistence.usuario.UsuarioService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Optional<Usuario> usuario = usuarioService.findByUserName(username);

        return new User(usuario.get().getUsername(),
                usuario.get().getPassword(),
                usuario.get().isEnabled(),
                true, true, true,
                usuario.get().getAuthorities());
    }

}
