package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.coletaDoacao;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.usuario.Usuario;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.itemColetaDoacao.ItemColetaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.doador.Doador;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

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
    @Column(nullable = false, updatable = false)
    private long id;

    @Column(name = "esta_efetivada", nullable = false)
    private boolean estaEfetivada;

    @NotNull(message = "É obrigatório informar a data da coleta")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "data_doacao", nullable = false, updatable = false)
    private LocalDate dataDoacao;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "data_efetivacao")
    private LocalDate dataEfetivacao;

    private String observacao;

    @Column(name = "esta_cancelada", nullable = false)
    private boolean estaCancelada;

    @Column(name = "motivo_cancelamento")
    private String motivoCancelamento;

    @ManyToOne
    private Usuario usuarioEfetivacao;

    @ManyToOne
    private Usuario usuarioCancelamento;

    @NotNull(message = "É obrigatório informar o usuário que fez o registro da coleta")
    @ManyToOne
    @JoinColumn(nullable = false)
    private Usuario usuarioRegistro;

    @NotNull(message = "É obrigatório informar ao menos um item na coleta")
    @OneToMany(mappedBy = "coletaDoacao")
    private List<ItemColetaDoacao> itensColeta;

    @NotNull(message = "É obrigatório informar o doador para a coleta")
    @ManyToOne
    @JoinColumn(name = "doador_id", referencedColumnName = "id", nullable = false)
    private Doador doador;

}
