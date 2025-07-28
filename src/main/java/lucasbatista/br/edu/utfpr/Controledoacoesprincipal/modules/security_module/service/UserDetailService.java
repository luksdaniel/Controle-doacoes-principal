package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.service;

import lombok.RequiredArgsConstructor;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.entity.Usuario;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UsuarioRepository usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Optional<Usuario> usuario = usuarioService.findByUsername(username);

        return new User(usuario.get().getUsername(),
                usuario.get().getPassword(),
                usuario.get().isEnabled(),
                true, true, true,
                usuario.get().getAuthorities());
    }

}
