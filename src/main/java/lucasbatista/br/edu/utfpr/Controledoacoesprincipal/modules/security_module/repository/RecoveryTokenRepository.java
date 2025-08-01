package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.repository;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.entity.RecoveryToken;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecoveryTokenRepository extends JpaRepository<RecoveryToken, Long> {

    Optional<RecoveryToken> findByToken(String token);
    Optional<RecoveryToken> findByUsuario(Usuario usuario);

}