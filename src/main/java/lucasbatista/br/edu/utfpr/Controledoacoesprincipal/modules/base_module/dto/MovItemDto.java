package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class MovItemDto implements Serializable {

    private LocalDate dataMovimentacao;

    private String descricaoItem;

    private double quantidadeMovimentada;

    private String tipoDocumento;

}
