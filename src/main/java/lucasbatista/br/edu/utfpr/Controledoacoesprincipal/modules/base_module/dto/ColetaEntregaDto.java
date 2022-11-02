package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class ColetaEntregaDto implements Comparable<ColetaEntregaDto> {

    private LocalDate data;

    private String tipoDocumento;

    private String doadorBeneficiario;

    private String descricaoItem;

    private double quantidadeMovimentada;

    @Override
    public int compareTo(ColetaEntregaDto o) {
        if (this.getData().isBefore(o.getData()))
            return -1;
        if (this.getData().isAfter(o.getData()))
            return 1;

        return 0;
    }
}
