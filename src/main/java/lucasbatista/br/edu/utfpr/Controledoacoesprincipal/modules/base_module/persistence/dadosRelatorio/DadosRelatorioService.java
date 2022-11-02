package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.persistence.dadosRelatorio;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.dto.ColetaEntregaDto;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.dto.MovItemDto;

import java.util.List;

public interface DadosRelatorioService {

    public List<ColetaEntregaDto> getDadosRelatorioColetaEntrega();

    public List<MovItemDto> findAllMovimentacoes(long itemId);

}
