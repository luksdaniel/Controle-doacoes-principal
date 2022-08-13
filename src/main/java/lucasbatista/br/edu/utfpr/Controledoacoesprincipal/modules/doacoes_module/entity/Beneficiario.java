package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity;

import lombok.*;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.Pessoa;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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

    @Column(name = "procurou_cras")
    private boolean procurouCras;

    @Column(name = "tem_casa_propria")
    private boolean temCasaPropria;

    @Column(name = "possui_criancas")
    private boolean possuiCriancas;

    @Column(name = "quantidade_moradores_casa")
    private int quantidadeMoradoresCasa;

    @Column(name = "quantidade_criancas")
    private int quantidadeCriancas;

    @Column(name = "possui_idosos")
    private boolean possuiIdosos;

    @Column(name = "quantidade_idosos")
    private int quantidadeIdosos;

    @Column(name = "renda_familiar")
    private double rendaFamiliar;

    @Column(name = "tipo_atendimento")
    private String tipoAtendimento;

    @Column(name = "dias_entre_doacao")
    private int diasEntreDoacao;

    @JsonIgnore
    @OneToMany(mappedBy = "beneficiario")
    private Set<EntregaDoacao> EntregasDoacao = new HashSet<>();

}
