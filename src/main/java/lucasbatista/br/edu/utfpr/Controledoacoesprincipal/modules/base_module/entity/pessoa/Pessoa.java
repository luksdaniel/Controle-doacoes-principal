package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.pessoa;

import lombok.*;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.Enumerators.TipoPessoa;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.endereco.Endereco;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Table(name = "pessoa")
@MappedSuperclass
public class Pessoa extends RepresentationModel<Pessoa> implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;

    @NotBlank()
    @Column(name = "nome_fantasia", nullable = false)
    private String nomeFantasia;

    @NotBlank()
    @Column(name = "razao_social", nullable = false)
    private String razaoSocial;

    @CPF(message = "CPF Inválido")
    @Column(name = "cpf")
    private String cpf;

    @CNPJ(message = "CNPJ Inválido")
    @Column(name = "cnpj")
    private String cnpj;

    @Email
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "telefone", nullable = false)
    private String telefone;

    @Column(name = "tipo_pessoa", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoPessoa tipoPessoa;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    private Endereco endereco;

    @Column(name = "esta_cancelado", nullable = false)
    private boolean estaCancelao;

    @Column(name = "data_cadastro",nullable = false)
    private LocalDate dataCadastro;

}
