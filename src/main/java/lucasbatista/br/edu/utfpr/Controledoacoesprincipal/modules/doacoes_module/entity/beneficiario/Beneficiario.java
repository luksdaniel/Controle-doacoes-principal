package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.beneficiario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.pessoa.Pessoa;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.entregaDoacao.EntregaDoacao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "beneficiario")
public class Beneficiario extends Pessoa implements Serializable {

    @NotNull
    @Column(name = "procurou_cras", nullable = false)
    private boolean procurouCras;

    @NotNull
    @Column(name = "tem_casa_propria", nullable = false)
    private boolean temCasaPropria;

    @NotNull
    @Column(name = "possui_criancas", nullable = false)
    private boolean possuiCriancas;

    @NotNull
    @Min(1)
    @Column(name = "quantidade_moradores_casa", nullable = false)
    private int quantidadeMoradoresCasa;

    @Min(0)
    @NotNull
    @Column(name = "quantidade_criancas", nullable = false)
    private int quantidadeCriancas;

    @NotNull
    @Column(name = "possui_idosos", nullable = false)
    private boolean possuiIdosos;

    @Min(0)
    @NotNull
    @Column(name = "quantidade_idosos", nullable = false)
    private int quantidadeIdosos;

    @Min(0)
    @NotNull
    @Column(name = "renda_familiar", nullable = false)
    private double rendaFamiliar;

    @Column(name = "tipo_atendimento")
    private String tipoAtendimento;

    @Column(name = "dias_entre_doacao")
    private int diasEntreDoacao;

    @JsonIgnore
    @OneToMany(mappedBy = "beneficiario")
    private Set<EntregaDoacao> EntregasDoacao = new HashSet<>();

}
