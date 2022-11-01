package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class ColetaEntregaDto {

    private LocalDate data;

    private String tipoDocumento;

    private String doadorBeneficiario;

    private String descricaoItem;

    private double quantidadeMovimentada;

}
