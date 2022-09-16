package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.ajusteManualEstoque;

import lombok.*;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.item.Item;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.usuario.Usuario;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "ajuste_manual_estoque")
public class AjusteManualEstoque extends RepresentationModel<AjusteManualEstoque>implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;

    @Column(name = "data_ajuste", nullable = false)
    private LocalDate dataAjuste;

    @NotNull
    @Column(name = "quantidade_movimentada", nullable = false)
    private double quantidadeMovimentada;

    private String observacao;

    @Column(name = "esta_cancelada")
    private boolean estaCancelada;

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private Item item;

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private Usuario usuarioResponsavel;

}
