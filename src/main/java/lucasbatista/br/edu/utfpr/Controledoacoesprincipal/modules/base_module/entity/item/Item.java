package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.item;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.unidadeMedida.UnidadeMedida;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.ItemEntregaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.itemColetaDoacao.ItemColetaDoacao;

import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotBlank(message = "É obrigatório informar a descrição")
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "informacoes_adicionais")
    private String informacoesAdicionais;

    @Column(name = "esta_cancelado", nullable = false)
    private boolean estaCancelado;

    @Min(value = 0, message = "A quantidade mínima não pode ser menor que 0")
    @NotNull(message = "É obrigatório informar a quantidade mínima")
    @Column(name = "quantidade_minima", nullable = false)
    private double quantidadeMinima;

    @Min(value = 0, message = "A quantidade em estoque não pode ser menor que 0")
    @NotNull(message = "É obrigatório informar a quantidade em estoque")
    @Column(name = "quantidade_estoque", nullable = false)
    private double quantidadeEstoque;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "data_cadastro", nullable = false)
    private LocalDate dataCadastro;

    @NotBlank(message = "É obrigatório informar o tipo do item")
    @Column(name = "tipo_item", nullable = false)
    private String tipoItem;

    @NotNull(message = "Deve ser informada uma unidade de medida")
    @ManyToOne
    @JoinColumn(name = "unidade_medida_id", referencedColumnName = "id", nullable = false)
    private UnidadeMedida unidadeMedida;

    @JsonIgnore
    @OneToMany(mappedBy = "item")
    private Set<ItemColetaDoacao> coletasDoacao = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "item")
    private Set<ItemEntregaDoacao> entregasDoacao = new HashSet<>();
}
