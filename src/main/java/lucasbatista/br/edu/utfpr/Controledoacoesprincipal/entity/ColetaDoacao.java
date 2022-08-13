package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.entity;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "coleta_doacao")
public class ColetaDoacao extends RepresentationModel<ColetaDoacao> {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "esta_efetivada")
    private boolean estaEfetivada;

    @Column(name = "data_doacao")
    private LocalDate dataDoacao;

    @Column(name = "data_efetivacao")
    private LocalDate dataEfetivacao;

    private String observacao;

    @Column(name = "esta_cancelada")
    private boolean estaCancelada;

    @Column(name = "motivo_cancelamento")
    private String motivoCancelamento;

    @OneToOne
    private Usuario usuarioEfetivacao;


    //private Set<Item> itemColeta = new HashSet<>();

}
