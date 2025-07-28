package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import lombok.*;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.Pessoa;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@Table(name = "beneficiario")
public class Beneficiario extends Pessoa implements Serializable {

    @NotNull(message = "É obrigatório informar se o Beneficiário procurou o crás")
    @Column(name = "procurou_cras", nullable = false)
    private boolean procurouCras;

    @NotNull(message = "É obrigatório informar se o Beneficiário tem casa própria")
    @Column(name = "tem_casa_propria", nullable = false)
    private boolean temCasaPropria;

    @NotNull(message = "É obrigatório informar se o Beneficiário possúi crianças")
    @Column(name = "possui_criancas", nullable = false)
    private boolean possuiCriancas;

    @NotNull(message = "É obrigatório informar a quantidade de moradores na casa")
    @Min(value = 1, message = "Deve haver pelo menos um morador na casa")
    @Column(name = "quantidade_moradores_casa", nullable = false)
    private int quantidadeMoradoresCasa;

    @Min(value = 0, message = "A quantidade de crianças não pode ser menor que zero")
    @NotNull(message = "É obrigatório informar a quantidade de crianças na casa")
    @Column(name = "quantidade_criancas", nullable = false)
    private int quantidadeCriancas;

    @NotNull(message = "É obrigatório informar se o Beneficiário possuí idosos em casa")
    @Column(name = "possui_idosos", nullable = false)
    private boolean possuiIdosos;

    @Min(value = 0, message = "A quantidade de idosos não pode ser menor que zero")
    @NotNull(message = "É obrigatório informar a quantidade de idosos na casa")
    @Column(name = "quantidade_idosos", nullable = false)
    private int quantidadeIdosos;

    @Min(value = 0, message = "A renda familiar não pode ser menor que zero")
    @NotNull(message = "É obrigatório informar a renda familiar")
    @Column(name = "renda_familiar", nullable = false)
    private double rendaFamiliar;

    @Column(name = "tipo_atendimento")
    private String tipoAtendimento;

    @Column(name = "dias_entre_doacao")
    private int diasEntreDoacao;

    @JsonIgnore
    @OneToMany(mappedBy = "beneficiario")
    private Set<EntregaDoacao> EntregasDoacao = new HashSet<>();

    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;
        if (!super.equals(o)) return true;
        return this.getId() == ((Beneficiario) o).getId();
    }

    public int hashCode(){
        return 7 * 31 + Long.hashCode(this.getId());
    }
}
