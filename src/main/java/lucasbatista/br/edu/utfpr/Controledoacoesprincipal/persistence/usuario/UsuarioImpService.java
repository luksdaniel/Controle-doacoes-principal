package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.persistence.usuario;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.entity.Endereco;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.entity.Usuario;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioImpService implements UsuarioService{

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> findAllUsuario() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario saveUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario updateUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
