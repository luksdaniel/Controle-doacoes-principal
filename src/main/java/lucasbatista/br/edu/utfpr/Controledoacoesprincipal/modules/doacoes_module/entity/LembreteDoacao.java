package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "lembrete_doacao")
public class LembreteDoacao extends RepresentationModel<LembreteDoacao> implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "data_agendamento")
    private LocalDate dataAgendamento;

    @Column(name = "data_lembrete")
    private LocalDate dataLembrete;

    private String mensagem;

    @Column(name = "repetir_todo_mes")
    private boolean repetirTodoMes;

}
