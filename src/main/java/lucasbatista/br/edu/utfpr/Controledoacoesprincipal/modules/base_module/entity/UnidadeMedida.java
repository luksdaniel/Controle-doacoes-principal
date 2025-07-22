package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@Table(name = "unidade_medida")
public class UnidadeMedida extends RepresentationModel<UnidadeMedida> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;

    @NotBlank(message = "É obrigatório informar a sigla")
    @Length(max = 3, message = "A sigla pode conter até 3 caracteres")
    @Column(nullable = false, length = 3)
    private String sigla;

    @NotBlank(message = "É obrigatório informar a descrição")
    @Column(nullable = false)
    private String descricao;

    @Column(name = "esta_cancelado", nullable = false)
    private boolean estaCancelado;

    @JsonIgnore
    @OneToMany(mappedBy = "unidadeMedida")
    private Set<Item> itensDaUnidade = new HashSet<>();

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;
        if (!super.equals(o)) return false;
        UnidadeMedida that = (UnidadeMedida) o;
        return (that.id == this.id);
    }

}
