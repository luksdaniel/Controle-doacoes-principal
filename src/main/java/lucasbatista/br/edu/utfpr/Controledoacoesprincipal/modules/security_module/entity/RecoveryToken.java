package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.entity.usuario.Usuario;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RecoveryToken{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    private String uuid = UUID.randomUUID().toString();

    private String token;

    @OneToOne
    @JoinColumn(nullable = false, name = "usuario_id")
    private Usuario usuario;

    private LocalDateTime expiryDate;

    private LocalDateTime calculateExpiryDate(int expiryTime){
        return LocalDateTime.now().plusMinutes(expiryTime);
    }

}