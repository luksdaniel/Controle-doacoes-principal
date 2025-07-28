package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.entity.Usuario;
import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@ToString
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

    //@NotNull(message = "É obrigatório informar a data da coleta")
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

    @ManyToOne()
    private Usuario usuarioEfetivacao;

    @ManyToOne()
    private Usuario usuarioCancelamento;

    @NotNull(message = "É obrigatório informar o usuário que fez o registro da coleta")
    @ManyToOne
    @JoinColumn(nullable = false)
    private Usuario usuarioRegistro;

    @NotNull(message = "É obrigatório informar ao menos um item na coleta")
    @OneToMany(mappedBy = "coletaDoacao", fetch = FetchType.EAGER)
    private List<ItemColetaDoacao> itensColeta;

    @NotNull(message = "É obrigatório informar o doador para a coleta")
    @ManyToOne
    @JoinColumn(name = "doador_id", referencedColumnName = "id", nullable = false)
    private Doador doador;

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;
        if (!super.equals(o)) return false;
        return (this.id == ((ColetaDoacao) o).getId());
    }

    @Override
    public int hashCode(){
        return 7 * 31 + Long.hashCode(this.id);
    }

}
