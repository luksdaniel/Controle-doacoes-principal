package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.itemColetaDoacao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.item.Item;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.coletaDoacao.ColetaDoacao;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "item_coleta_doacao",
        uniqueConstraints = {@UniqueConstraint(name = "ukItemColetaDoacao", columnNames = {"item_id", "coleta_doacao_id"})
})
public class ItemColetaDoacao extends RepresentationModel<ItemColetaDoacao> implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;

    @DecimalMin(value = "0.001", message = "A quantidade deve ser maior que zero")
    @Column(nullable = false)
    private double quantidade;

    //private String observacao;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "data_inclusao", nullable = false)
    private LocalDate dataInclusao;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "coleta_doacao_id", referencedColumnName = "id", nullable = false)
    private ColetaDoacao coletaDoacao;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

}
