package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity;

import lombok.*;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.usuario.Usuario;
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
@Table(name = "ajuste_manual_estoque")
public class AjusteManualEstoque extends RepresentationModel<AjusteManualEstoque>implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "data_ajuste")
    private LocalDate dataAjuste;

    private String observacao;

    @Column(name = "esta_cancelada")
    private boolean estaCancelada;

    @ManyToOne
    private Usuario usuarioResponsavel;

}
