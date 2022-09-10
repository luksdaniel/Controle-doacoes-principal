package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.usuario;

import lombok.*;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.instituicao.Instituicao;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
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
    private long id;

    private String login;
    private String senha;

    @Column(name = "from_google")
    private boolean eDoGoogle;

    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;

    @Column(name = "esta_logado")
    private boolean estaLogado;

    @Column(name = "esta_cancelado")
    private boolean estaCancelado;

    @ManyToOne
    @JoinColumn(name = "instituicao_id",referencedColumnName = "id")
    private Instituicao instituicao;

}
