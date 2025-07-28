package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.Pessoa;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "doador",
        uniqueConstraints = {@UniqueConstraint(name = "ukEmailDoador", columnNames = {"email"})
})
public class Doador extends Pessoa implements Serializable {

    @NotNull(message = "É obrigatório informar se o doador recebe e-mails")
    @Column(name = "recebe_emails", nullable = false)
    private boolean recebeEmails;
/*
    @JsonIgnore
    @OneToOne
    private Usuario usuario;
*/
    @JsonIgnore
    @OneToMany(mappedBy = "doador")
    private Set<ColetaDoacao> coletasDoacao = new HashSet<>();

}
