package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.usuario;

import lombok.*;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.instituicao.Instituicao;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "usuario")
public class Usuario extends RepresentationModel<Usuario> implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;

    @NotBlank(message = "É obrigatório informar o login")
    @Column(nullable = false)
    private String login;

    @NotBlank(message = "É obrigatório informar a senha")
    @Column(nullable = false)
    private String senha;

    @NotNull(message = "É obrigatório informar se o usuário é do google")
    @Column(name = "from_google", nullable = false)
    private boolean eDoGoogle;

    @Column(name = "data_cadastro", nullable = false)
    private LocalDate dataCadastro;

    @Column(name = "esta_cancelado", nullable = false)
    private boolean estaCancelado;

    @ManyToOne
    @JoinColumn(name = "instituicao_id",referencedColumnName = "id")
    private Instituicao instituicao;

}
