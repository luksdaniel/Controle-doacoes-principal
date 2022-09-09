package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.unidadeMedida;

import lombok.*;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.item.Item;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotBlank(message = "Sigla não pode ser nula")
    @Column(nullable = false)
    private String sigla;

    @NotBlank
    @Column(nullable = false)
    private String descricao;

    @NotNull
    @Column(name = "esta_cancelado", nullable = false)
    private boolean estaCancelado;

    @JsonIgnore
    @OneToMany(mappedBy = "unidadeMedida")
    private Set<Item> itensDaUnidade = new HashSet<>();

}
