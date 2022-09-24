package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity;

import lombok.*;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.item.Item;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.usuario.Usuario;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.beneficiario.Beneficiario;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serializable;
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
@Table(name = "entrega_doacao")
public class EntregaDoacao extends RepresentationModel<EntregaDoacao> implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String descricao;

    @Column(name = "data_entrega")
    private LocalDate dataEntrega;

    private String observacao;

    @Column(name = "esta_cancelada")
    private boolean estaCancelada;

    @Column(name = "nome_responsavel")
    private String nomeResponsavel;

    @ManyToOne
    private Usuario usuarioRegistro;

    @ManyToMany
    @JoinTable(
            name = "item_entrega_doacao",
            joinColumns = @JoinColumn(name = "entrega_doacao_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")

    )
    private Set<Item> itemEntrega = new HashSet<>();


    @ManyToOne
    @JoinColumn(name = "beneficiario_id", referencedColumnName = "id")
    private Beneficiario beneficiario;

}
