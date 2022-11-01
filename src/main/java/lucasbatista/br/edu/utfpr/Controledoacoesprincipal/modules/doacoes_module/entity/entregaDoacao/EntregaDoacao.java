package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.entregaDoacao;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.entity.usuario.Usuario;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.itemEntregaDoacao.ItemEntregaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.beneficiario.Beneficiario;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

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
    @Column(nullable = false, updatable = false)
    private long id;

    //@NotNull(message = "É obrigatório informa a data da entrega")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "data_entrega", nullable = false, updatable = false)
    private LocalDate dataEntrega;

    private String observacao;

    @Column(name = "esta_cancelada", nullable = false)
    private boolean estaCancelada;

    @Column(name = "motivo_cancelamento")
    private String motivoCancelamento;

    @ManyToOne
    private Usuario usuarioCancelamento;

    @NotBlank(message = "É obrigatório informar o nome do responsável")
    @Column(name = "nome_responsavel", nullable = false)
    private String nomeResponsavel;

    @NotNull(message = "É obrigatório informar o usuário que fez o registro da entrega")
    @ManyToOne
    @JoinColumn(nullable = false)
    private Usuario usuarioRegistro;

    @NotNull(message = "É obrigatório informar os itens da entrega")
    @OneToMany(mappedBy = "entregaDoacao", fetch = FetchType.EAGER)
    private List<ItemEntregaDoacao> itensEntrega;

    @NotNull(message = "É obrigatório informar o beneficiário da entrega")
    @ManyToOne
    @JoinColumn(name = "beneficiario_id", referencedColumnName = "id", nullable = false)
    private Beneficiario beneficiario;

}
