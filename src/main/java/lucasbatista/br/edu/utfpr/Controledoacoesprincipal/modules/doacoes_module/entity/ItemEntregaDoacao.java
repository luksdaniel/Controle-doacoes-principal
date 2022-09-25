package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.item.Item;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.entregaDoacao.EntregaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.itemColetaDoacao.ItemColetaDoacao;
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
@Table(name = "item_entrega_doacao",
        uniqueConstraints = {@UniqueConstraint(name = "ukItemEntregaDoacao", columnNames = {"item_id", "entrega_doacao_id"})
})
public class ItemEntregaDoacao extends RepresentationModel<ItemColetaDoacao> implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double quantidade;

    private String observacao;

    private LocalDate dataInclusao;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "entrega_doacao_id", referencedColumnName = "id", nullable = false)
    private EntregaDoacao entregaDoacao;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

}
