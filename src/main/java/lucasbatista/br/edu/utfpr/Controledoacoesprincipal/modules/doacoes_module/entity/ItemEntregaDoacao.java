package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.Item;
import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@Table(name = "item_entrega_doacao",
        uniqueConstraints = {@UniqueConstraint(name = "ukItemEntregaDoacao", columnNames = {"item_id", "entrega_doacao_id"})
})
public class ItemEntregaDoacao extends RepresentationModel<ItemColetaDoacao> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;

    @DecimalMin(value = "0.001", message = "A quantidade deve ser maior que zero")
    @Column(nullable = false)
    private double quantidade;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "data_inclusao", nullable = false)
    private LocalDate dataInclusao;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "entrega_doacao_id", referencedColumnName = "id", nullable = false)
    private EntregaDoacao entregaDoacao;

    @NotNull(message = "É obrigatório informar o item da entrega!")
    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Override
    public int hashCode(){
        return 7 * 31 + Long.hashCode(this.id);
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        return (this.id == ((ItemEntregaDoacao) o).getId());
    }
}
