package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.instituicao;

import lombok.*;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.pessoa.Pessoa;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.usuario.Usuario;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
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
@Table(name = "instituicao")
public class Instituicao extends Pessoa implements Serializable {

    @Column(name = "horario_funcionamento")
    private String horarioFuncionamento;

    @Column(name = "data_implantacao", nullable = false)
    private LocalDate dataImplantacao;

    @NotNull
    @Column(name = "dias_entre_doacao",nullable = false)
    private int diasEntreDoacao;

    @JsonIgnore
    @OneToMany(mappedBy = "instituicao")
    private Set<Usuario> usuarios = new HashSet<>();

}
