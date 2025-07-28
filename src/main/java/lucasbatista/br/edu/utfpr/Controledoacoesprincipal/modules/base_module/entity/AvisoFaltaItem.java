package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@Table(name = "aviso_falta_item")
public class AvisoFaltaItem extends RepresentationModel<AvisoFaltaItem> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "data_agendamento")
    private LocalDate dataAgendamento;

    @Column(name = "data_lembrete")
    private LocalDate dataLembrete;

    private String mensagem;

    @Override
    public int hashCode(){
        return 7 * 31 + Long.hashCode(id);
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;
        if (!super.equals(o)) return false;
        AvisoFaltaItem that = (AvisoFaltaItem) o;
        return id == that.id;
    }

}
