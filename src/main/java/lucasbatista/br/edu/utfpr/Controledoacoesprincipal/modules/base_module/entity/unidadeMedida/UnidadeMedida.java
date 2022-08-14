package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.unidadeMedida;

import lombok.*;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.item.Item;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
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
    private long id;
    private String sigla;
    private String descricao;

    @Column(name = "esta_cancelado")
    private boolean estaCancelado;

    @JsonIgnore
    @OneToMany(mappedBy = "unidadeMedida")
    private Set<Item> itensDaUnidade = new HashSet<>();

}