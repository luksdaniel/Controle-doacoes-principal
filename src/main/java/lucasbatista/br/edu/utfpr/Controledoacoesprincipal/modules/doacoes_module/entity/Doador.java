package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity;

import lombok.*;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.Pessoa;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.Usuario;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.jmx.export.annotation.ManagedNotification;

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
@Table(name = "doador")
public class Doador extends Pessoa implements Serializable {

    @Column(name = "esta_cancelado")
    private boolean estaCancelao;

    @Column(name = "recebe_emails")
    private boolean recebeEmails;

    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;

    @JsonIgnore
    @OneToOne
    private Usuario usuario;

    @JsonIgnore
    @OneToMany(mappedBy = "doador")
    private Set<ColetaDoacao> coletasDoacao = new HashSet<>();

}
