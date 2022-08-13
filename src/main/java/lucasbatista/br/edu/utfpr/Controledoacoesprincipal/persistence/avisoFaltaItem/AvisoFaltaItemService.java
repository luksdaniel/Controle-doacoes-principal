package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.persistence.avisoFaltaItem;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.entity.AvisoFaltaItem;

import java.util.List;
import java.util.Optional;

public interface AvisoFaltaItemService {

    List<AvisoFaltaItem> findAllEndereco();

    Optional<AvisoFaltaItem> findById(Long id);

    AvisoFaltaItem saveAvisoFaltaItem(AvisoFaltaItem avisoFaltaItem);

    AvisoFaltaItem updateAvisoFaltaItem(AvisoFaltaItem avisoFaltaItem);

    void deleteAvisoFaltaItem(Long id);

}
