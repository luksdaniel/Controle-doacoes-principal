package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.entity.usuario;

import lombok.*;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.Enumerators.Role;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.instituicao.Instituicao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.doador.Doador;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "usuario", uniqueConstraints = {
        @UniqueConstraint(name = "ukUsuario", columnNames = {"username"})
})
public class Usuario extends RepresentationModel<Usuario> implements Serializable, UserDetails {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;

    @NotBlank(message = "É obrigatório informar o login")
    @Column(nullable = false)
    private String username;

    @NotBlank(message = "É obrigatório informar a senha")
    @Column(nullable = false)
    private String password;

    @NotNull(message = "É obrigatório informar se o usuário é do google")
    @Column(name = "from_google", nullable = false)
    private boolean doGoogle;

    @Column(name = "data_cadastro", nullable = false)
    private LocalDate dataCadastro;

    @Column(name = "esta_cancelado", nullable = false)
    private boolean cancelado;

    @Column
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> role = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "instituicao_id",referencedColumnName = "id")
    private Instituicao instituicao;

    @OneToOne
    private Doador doador;




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (var r : this.role){
            var sga = new SimpleGrantedAuthority(r.name());
            authorities.add(sga);
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !this.cancelado;
    }
}
