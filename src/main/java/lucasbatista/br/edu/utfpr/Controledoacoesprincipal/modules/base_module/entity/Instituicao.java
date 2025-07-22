package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.entity.usuario.Usuario;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@Table(name = "instituicao",
        uniqueConstraints = {@UniqueConstraint(name = "ukEmailInstituicao", columnNames = {"email"})
})
public class Instituicao extends Pessoa implements Serializable {

    @NotBlank(message = "É obrigatório informar o horário de funcionamento")
    @Column(name = "horario_funcionamento", nullable = false)
    private String horarioFuncionamento;

    @Column(name = "data_implantacao"/*, nullable = false*/)
    private LocalDate dataImplantacao;

    @NotNull(message = "É obrigatório informar os dias entre doação")
    @Column(name = "dias_entre_doacao",nullable = false)
    private int diasEntreDoacao;

    @JsonIgnore
    @OneToMany(mappedBy = "instituicao")
    private Set<Usuario> usuarios = new HashSet<>();

}
