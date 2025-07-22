package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.avisoFaltaItem;

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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "aviso_falta_item")
public class AvisoFaltaItem extends RepresentationModel<AvisoFaltaItem> implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "data_agendamento")
    private LocalDate dataAgendamento;

    @Column(name = "data_lembrete")
    private LocalDate dataLembrete;

    private String mensagem;

}
