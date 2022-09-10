package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.item;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.unidadeMedida.UnidadeMedida;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.ColetaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.EntregaDoacao;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.Formula;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
@Table(name = "item")
public class Item extends RepresentationModel<Item> implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;

    @NotBlank()
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "informacoes_adicionais")
    private String informacoesAdicionais;

    @NotNull
    @Column(name = "esta_cancelado", nullable = false)
    private boolean estaCancelado;

    @NotNull
    @Column(name = "quantidade_minima", nullable = false)
    private double quantidadeMinima;

    @NotNull
    @Column(name = "quantidade_estoque", nullable = false)
    private double quantidadeEstoque;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "data_cadastro", nullable = false)
    private LocalDate dataCadastro;

    @Column(name = "tipo_item")
    private String tipoItem;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "unidade_medida_id", referencedColumnName = "id", nullable = false)
    private UnidadeMedida unidadeMedida;

    @JsonIgnore
    @ManyToMany(mappedBy = "itemColeta")
    private Set<ColetaDoacao> coletasDoacao = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "itemEntrega")
    private Set<EntregaDoacao> entregasDoacao = new HashSet<>();

}
