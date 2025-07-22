package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.unidadeMedida;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.item.Item;
import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "unidade_medida")
public class UnidadeMedida extends RepresentationModel<UnidadeMedida> implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;

    @NotBlank(message = "É obrigatório informar a sigla")
    @Length(max = 3, message = "A sigla pode conter até 3 caracteres")
    @Column(nullable = false, length = 3)
    private String sigla;

    @NotBlank(message = "É obrigatório informar a descrição")
    @Column(nullable = false)
    private String descricao;

    @Column(name = "esta_cancelado", nullable = false)
    private boolean estaCancelado;

    @JsonIgnore
    @OneToMany(mappedBy = "unidadeMedida")
    private Set<Item> itensDaUnidade = new HashSet<>();

}
