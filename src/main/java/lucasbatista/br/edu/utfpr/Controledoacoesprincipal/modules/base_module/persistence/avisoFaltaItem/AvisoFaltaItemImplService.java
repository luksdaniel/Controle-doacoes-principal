package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.persistence.avisoFaltaItem;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.AvisoFaltaItem;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.repository.AvisoFaltaItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvisoFaltaItemImplService implements AvisoFaltaItemService{

    @Autowired
    AvisoFaltaItemRepository avisoFaltaItemRepository;

    @Override
    public List<AvisoFaltaItem> findAllEndereco() {
        return avisoFaltaItemRepository.findAll();
    }

    @Override
    public Optional<AvisoFaltaItem> findById(Long id) {
        return avisoFaltaItemRepository.findById(id);
    }

    @Override
    public AvisoFaltaItem saveAvisoFaltaItem(AvisoFaltaItem avisoFaltaItem) {
        return avisoFaltaItemRepository.save(avisoFaltaItem);
    }

    @Override
    public AvisoFaltaItem updateAvisoFaltaItem(AvisoFaltaItem avisoFaltaItem) {
        return avisoFaltaItemRepository.save(avisoFaltaItem);
    }

    @Override
    public void deleteAvisoFaltaItem(Long id) {
        avisoFaltaItemRepository.deleteById(id);
    }
}
