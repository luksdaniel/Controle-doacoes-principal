package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.Enumerators.TipoPessoa;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@MappedSuperclass
public class Pessoa extends RepresentationModel<Pessoa> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;

    @NotBlank(message = "É obrigatório informar o nome fantasia")
    @Column(name = "nome_fantasia", nullable = false)
    private String nomeFantasia;

    @Column(name = "razao_social")
    private String razaoSocial;

    @Column(name = "cpfCnpj", nullable = false)
    private String cpfCnpj;

    @Email(message = "Email inválido")
    @Column(name = "email")
    private String email;

    @NotNull(message = "É obrigatório informar o telefone")
    @Column(name = "telefone", nullable = false)
    private String telefone;

    @Column(name = "tipo_pessoa", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoPessoa tipoPessoa;

    @NotNull(message = "É obrigatório informar o endereço")
    @OneToOne(cascade = CascadeType.ALL)
    private Endereco endereco;

    @Column(name = "esta_cancelado", nullable = false)
    private boolean estaCancelado;

    @Column(name = "data_cadastro",nullable = false)
    private LocalDate dataCadastro;

    @Override
    public boolean equals(Object o){
        if (o == this) return true;
        if (o == null || o.getClass() != getClass()) return false;
        if (!super.equals(o)) return false;
        Pessoa that =  (Pessoa) o;
        return (this.id == that.id);
    }

    @Override
    public int hashCode(){
        return 7 * 31 + Long.hashCode(this.id);
    }

}
