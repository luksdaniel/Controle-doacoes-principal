package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.entity;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

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
    private long id;

    @Column(name = "nome_fantasia")
    private String nomeFantasia;

    @Column(name = "razao_social")
    private String razaoSocial;

    @Column(name = "cpf_ou_cnpj")
    private int cpfOuCnpj;

    private String email;
    private String telefone;

    @Column(name = "tipo_pessoa")
    private String tipoPessoa;

    @OneToOne(cascade = CascadeType.ALL)
    private Endereco endereco;

}
