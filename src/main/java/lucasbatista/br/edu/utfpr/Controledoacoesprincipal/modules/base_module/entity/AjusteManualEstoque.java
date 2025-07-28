package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity;

import lombok.*;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.entity.Usuario;
import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@Table(name = "ajuste_manual_estoque")
public class AjusteManualEstoque extends RepresentationModel<AjusteManualEstoque> implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;

    @Column(name = "data_ajuste", nullable = false)
    private LocalDate dataAjuste;

    @NotNull(message = "É obrigatório informar a quantidade movimentada")
    @Column(name = "quantidade_movimentada", nullable = false)
    private double quantidadeMovimentada;

    @NotBlank(message = "É obrigatório informar a observação")
    @Column(nullable = false, length = 5000)
    private String observacao;

    @Column(name = "esta_cancelada", nullable = false)
    private boolean estaCancelada;

    @NotNull(message = "É obrigatório informar o item")
    @ManyToOne
    @JoinColumn(nullable = false)
    private Item item;

    @NotNull(message = "É obrigatório informar o usuário")
    @ManyToOne
    @JoinColumn(nullable = false)
    private Usuario usuarioResponsavel;

    @Override
    public int hashCode(){
        return 7 * 31 + Long.hashCode(this.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AjusteManualEstoque that = (AjusteManualEstoque) o;
        return id == that.id;
    }
}
