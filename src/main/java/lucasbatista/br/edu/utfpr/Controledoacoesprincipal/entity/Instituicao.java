package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.entity;

import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
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

    @Column(name = "data_implantacao")
    private LocalDate dataImplantacao;

    @Column(name = "dias_entre_doacao")
    private int diasEntreDoacao;

    @JsonIgnore
    @OneToMany(mappedBy = "instituicao")
    private Set<Usuario> usuarios = new HashSet<>();

}
