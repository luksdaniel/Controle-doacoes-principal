package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.itemColetaDoacao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.itemColetaDoacao.ItemColetaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.repository.ItemColetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ItemColetaServiceImp implements ItemColetaService{

    @Autowired
    ItemColetaRepository itemColetaRepository;

    @Override
    public List<ItemColetaDoacao> findAllItemColetaDoacao() {
        return itemColetaRepository.findAll();
    }

    @Override
    public List<ItemColetaDoacao> findByColetaId(long id) {
        return itemColetaRepository.findByColetaDoacaoId(id);
    }

    @Override
    public Optional<ItemColetaDoacao> findById(Long id) {
        return itemColetaRepository.findById(id);
    }

    @Override
    public ItemColetaDoacao saveItenColeta(ItemColetaDoacao item) {
        return itemColetaRepository.save(item);
    }

    @Override
    public List<ItemColetaDoacao> saveItemAllColetaDoacao(List<ItemColetaDoacao> itens) {
        List<ItemColetaDoacao> itensInterno = new ArrayList<>();

        itensInterno.addAll(itemColetaRepository.saveAll(itens));
        return itensInterno;
    }

    @Override
    public ItemColetaDoacao updateItemColetaDoacao(ItemColetaDoacao itemColetaDoacao) {
        return itemColetaRepository.save(itemColetaDoacao);
    }

    @Override
    public void deleteItemColetaDoacao(Long id) {
        itemColetaRepository.deleteById(id);
    }
}
