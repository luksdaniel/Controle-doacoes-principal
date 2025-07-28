package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.entity.Usuario;
import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@Table(name = "entrega_doacao")
public class EntregaDoacao extends RepresentationModel<EntregaDoacao> implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;

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

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        return this.id == ((EntregaDoacao) o).getId();
    }

    @Override
    public int hashCode(){
        return 7 * 31 + Long.hashCode(this.id);
    }
}
