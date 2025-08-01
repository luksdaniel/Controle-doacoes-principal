package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.service.itemColetaDoacao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.ItemColetaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.repository.ItemColetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class ItemColetaService implements ItemColetaServiceBase {

    ItemColetaRepository itemColetaRepository;

    @Autowired
    public ItemColetaService(ItemColetaRepository itemColetaRepository) {
        this.itemColetaRepository = itemColetaRepository;
    }

    @Override
    public List<ItemColetaDoacao> saveAllItensColeta(List<ItemColetaDoacao> itens) {
        for (ItemColetaDoacao itemAtual: itens) {
            if (itemAtual.getDataInclusao() == null)
                itemAtual.setDataInclusao(LocalDate.now());
        }

        return itemColetaRepository.saveAll(itens);
    }

    @Override
    public ItemColetaDoacao saveItenColeta(ItemColetaDoacao item) {

        if (item.getDataInclusao() == null)
            item.setDataInclusao(LocalDate.now());

        return itemColetaRepository.save(item);
    }


    @Override
    public ItemColetaDoacao findByIdd(Long id) {
        Optional<ItemColetaDoacao> item = itemColetaRepository.findById(id);
        if(!item.isPresent()){
            throw new ResourceNotFoundException("Item não encontrado na doação");
        }else{
            return item.get();
        }
    }

    @Override
    public List<ItemColetaDoacao> updateAllItensColeta(List<ItemColetaDoacao> itens, long coletaId) {

        List<ItemColetaDoacao> listInternoColeta = itemColetaRepository.findByColetaDoacaoId(coletaId);
        boolean gravadoAndAtualizado;

        for (ItemColetaDoacao itemAtual: itens) {
            gravadoAndAtualizado = listInternoColeta.contains(itemAtual);

            if(gravadoAndAtualizado){
                if (itemAtual.getDataInclusao() == null)
                    itemAtual.setDataInclusao(LocalDate.now());
                itemColetaRepository.save(itemAtual);
            }else {
                saveItenColeta(itemAtual);
            }

        }
        for (ItemColetaDoacao itemAtual: listInternoColeta){
            gravadoAndAtualizado = itens.contains(itemAtual);
            if(!gravadoAndAtualizado)
                itemColetaRepository.deleteById(itemAtual.getId());

        }

        return itens;
    }
}
