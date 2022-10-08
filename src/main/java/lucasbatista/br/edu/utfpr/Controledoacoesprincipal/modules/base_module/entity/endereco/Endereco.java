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

    @NotBlank(message = "É obrigatório informar o logradouro")
    @Column(nullable = false)
    private String logradouro;

    @NotBlank(message = "É obrigatório informar a descrição do endereço")
    @Column(name = "descricao_endereco", nullable = false)
    private String descricaoEndereco;

    @NotNull(message = "É obrigatório informar o número")
    @Column(nullable = false)
    private int numero;

    @NotNull(message = "É obrigatório informar o CEP")
    @Column(nullable = false)
    private int cep;

    private String complemento;

}
