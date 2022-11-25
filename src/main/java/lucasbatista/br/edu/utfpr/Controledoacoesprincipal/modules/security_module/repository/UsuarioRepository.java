package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.repository;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.entity.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    Optional<Usuario> findByDoadorId(long id);

    Optional<Usuario> findByInstituicaoId(long id);
}
