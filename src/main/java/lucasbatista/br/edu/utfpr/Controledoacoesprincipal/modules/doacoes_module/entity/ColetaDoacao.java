package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity;

import lombok.*;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.item.Item;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.usuario.Usuario;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.doador.Doador;
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

    @ManyToOne
    private Usuario usuarioEfetivacao;

    @ManyToOne
    private Usuario usuarioRegistro;

    @ManyToMany
    @JoinTable(
            name = "item_coleta_doacao",
            joinColumns = @JoinColumn(name = "coleta_doacao_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")

    )
    private Set<Item> itemColeta = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "doador_id", referencedColumnName = "id")
    private Doador doador;

}
