package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity;

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
@Table(name = "lembrete_doacao")
public class LembreteDoacao extends RepresentationModel<LembreteDoacao> implements Serializable {

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

    @Column(name = "data_ultimo_envio")
    private LocalDate dataUltimoEnvio;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LembreteDoacao that = (LembreteDoacao) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return 7 * 31 + Long.hashCode(this.id);
    }
}
