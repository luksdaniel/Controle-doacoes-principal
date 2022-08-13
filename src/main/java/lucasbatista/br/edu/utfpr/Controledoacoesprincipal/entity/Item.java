package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.entity;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
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
    private long id;
    private String descricao;

    @Column(name = "informacoes_adicionais")
    private String informacoesAdicionais;

    @Column(name = "esta_cancelado")
    private boolean estaCancelado;

    @Column(name = "quantidade_minima")
    private double quantidadeMinima;

    @Column(name = "quantidade_estoque")
    private double quantidadeEstoque;

    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;

    @Column(name = "tipo_item")
    private String tipoItem;

    @ManyToOne
    @JoinColumn(name = "unidade_medida_id", referencedColumnName = "id")
    private UnidadeMedida unidadeMedida;

    //Set<ColetaDoacao> coletas = new HashSet<>();

}
