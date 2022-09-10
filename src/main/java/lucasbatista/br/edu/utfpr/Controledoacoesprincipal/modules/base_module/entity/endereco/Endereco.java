package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.endereco;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "endereco")
public class Endereco extends RepresentationModel<Endereco> implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;

    @NotBlank
    @Column(nullable = false)
    private String logradouro;

    @NotBlank
    @Column(name = "descricao_endereco", nullable = false)
    private String descricaoEndereco;

    @NotNull
    @Column(nullable = false)
    private int numero;

    @NotBlank
    @Column(nullable = false)
    private int cep;

    private String complemento;

}
