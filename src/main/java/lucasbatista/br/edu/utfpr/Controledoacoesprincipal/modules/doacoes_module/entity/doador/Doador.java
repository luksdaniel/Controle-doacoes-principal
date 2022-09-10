package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.doador;

import lombok.*;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.pessoa.Pessoa;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.usuario.Usuario;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.ColetaDoacao;
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
@Table(name = "doador")
public class Doador extends Pessoa implements Serializable {
/*
    @NotNull
    @Column(name = "esta_cancelado", nullable = false)
    private boolean estaCancelao;
*/
    @NotNull
    @Column(name = "recebe_emails", nullable = false)
    private boolean recebeEmails;
/*
    @Column(name = "data_cadastro",nullable = false)
    private LocalDate dataCadastro;
*/
    @JsonIgnore
    @OneToOne
    private Usuario usuario;

    @JsonIgnore
    @OneToMany(mappedBy = "doador")
    private Set<ColetaDoacao> coletasDoacao = new HashSet<>();

}
