package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.comparators;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.dto.ColetaEntregaDto;

import java.util.Comparator;

public class ColetaEntregaComparator implements Comparator<ColetaEntregaDto> {

    public int compare(ColetaEntregaDto o1, ColetaEntregaDto o2) {
        if (o1.getData().isBefore(o2.getData())){
            return -1;
        }else if(o1.getData().isAfter(o2.getData()))
            return 1;
        return 0;
    }
}
