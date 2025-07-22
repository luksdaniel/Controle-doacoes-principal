package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.endereco;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

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

    @NotBlank(message = "É obrigatório informar o bairro")
    @Column(name = "bairro", nullable = false)
    private String bairro;

    @NotNull(message = "É obrigatório informar o número")
    @Column(nullable = false)
    private int numero;

    @NotBlank(message = "É obrigatório informar o CEP")
    @Column(nullable = false)
    private String cep;

    @NotBlank(message = "É obrigatório informar a UF")
    @Column(nullable = false)
    private String uf;

    @NotBlank(message = "É obrigatório informar o município")
    @Column(nullable = false)
    private String municipio;

    private String complemento;

}
