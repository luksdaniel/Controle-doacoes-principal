package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.itemEntregaDoacao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.itemColetaDoacao.ItemColetaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.itemEntregaDoacao.ItemEntregaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.repository.ItemEntregaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemEntregaServiceImp implements ItemEntregaService{

    @Autowired
    ItemEntregaRepository itemEntregaRepository;

    @Override
    public List<ItemEntregaDoacao> findByEntregaId(long id) {
        return itemEntregaRepository.findByEntregaDoacaoId(id);
    }

    @Override
    public Optional<ItemEntregaDoacao> findById(Long id) {
        return itemEntregaRepository.findById(id);
    }

    @Override
    public ItemEntregaDoacao saveItemEntrega(ItemEntregaDoacao item) {
        return itemEntregaRepository.save(item);
    }

    @Override
    public List<ItemEntregaDoacao> saveAllItemEntrega(List<ItemEntregaDoacao> itemEntregaList) {
        List<ItemEntregaDoacao> itensInterno = new ArrayList<>();

        itensInterno.addAll(itemEntregaRepository.saveAll(itemEntregaList));
        return itensInterno;
    }

    @Override
    public ItemEntregaDoacao updateItemEntrega(ItemEntregaDoacao itemEntregaDoacao) {
        return itemEntregaRepository.save(itemEntregaDoacao);
    }

    @Override
    public void deleteItemEntregaDoacao(Long id) {
        itemEntregaRepository.deleteById(id);
    }
}
