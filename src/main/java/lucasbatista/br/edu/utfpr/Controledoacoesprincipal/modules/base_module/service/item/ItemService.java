package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.service.item;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.emailSender.EmailService;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.BusinessException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.DependencyNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceCreateErrorException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.Item;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.service.unidadeMedida.UnidadeMedidaServiceBase;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.repository.ItemRepository;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.service.doador.DoadorServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class ItemService implements ItemManager {

    ItemRepository itemRepository;
    UnidadeMedidaServiceBase unidadeMedidaManager;
    EmailService emailService;
    DoadorServiceBase doadorServiceBase;

    @Autowired
    public ItemService(
            ItemRepository itemRepository,
            UnidadeMedidaServiceBase unidadeMedidaManager,
            EmailService emailService,
            DoadorServiceBase doadorServiceBase) {
        this.itemRepository = itemRepository;
        this.unidadeMedidaManager = unidadeMedidaManager;
        this.emailService = emailService;
        this.doadorServiceBase = doadorServiceBase;
    }

    @Override
    public List<Item> findAllItem() {
        List<Item> itemList = itemRepository.findAll();
        if(itemList.isEmpty()){
            throw new ResourceNotFoundException("Itens não encontrados");
        }else{
            return itemList;
        }
    }

    @Override
    public List<Item> findAllActiveItem() {
        List<Item> itemList = itemRepository.findAll();
        if(itemList.isEmpty()){
            throw new ResourceNotFoundException("Itens não encontrados");
        }else{
            return itemList;
        }
    }

    @Override
    public Optional<Item> findById(Long id) {
        Optional<Item> item = itemRepository.findById(id);
        if(!item.isPresent()){
            throw new ResourceNotFoundException("Item não encontrado");
        }else{
            return item;
        }
    }

    @Override
    public Item saveItem(Item item) {
        validaUnidadeMedidaExistente(item);

        item.setUnidadeMedida(unidadeMedidaManager.findById(item.getUnidadeMedida().getId()).get());
        item.setDataCadastro(LocalDate.now());
        item.setEstaCancelado(false);
        Item itemInterno = itemRepository.save(item);

        if(itemInterno == null){
            throw new ResourceCreateErrorException("Não foi possível criar o item");
        }else{
            return itemInterno;
        }
    }

    @Override
    public Item updateItem(Item item) {
        validaUnidadeMedidaExistente(item);

        item.setUnidadeMedida(unidadeMedidaManager.findById(item.getUnidadeMedida().getId()).get());
        item.setDataCadastro(LocalDate.now());
        //item.setEstaCancelado(false);

        verificaItemJaCadastrado(item.getId());
        return (itemRepository.save(item));
    }

    @Override
    public Item cancelItem(Long id) {
        Optional<Item> item = itemRepository.findById(id);

        if (item.get().isEstaCancelado())
            throw new BusinessException("Item já cancelado");

        item.get().setEstaCancelado(true);

        return (itemRepository.save(item.get()));
    }

    @Override
    public Item uncanceItem(Long id) {
        Optional<Item> item = itemRepository.findById(id);

        if (!item.get().isEstaCancelado())
            throw new BusinessException("Item não está cancelado");

        item.get().setEstaCancelado(false);

        return (itemRepository.save(item.get()));
    }

    @Override
    public void validaAndMovimentaEstoque(Item item, double quantidade) {

        if (quantidade == 0)
            throw new BusinessException("É necessário informar a quantidade a ser movimentada do item");

        if ((item.getQuantidadeEstoque() + quantidade) < 0)
            throw new BusinessException("Não é possível subtrair do estoque do item, pois o deixaria negativo!");
        else
            item.setQuantidadeEstoque(item.getQuantidadeEstoque() + quantidade);

        //enviaAvisoFaltaItem(item);
        updateItem(item);

    }

    private void validaUnidadeMedidaExistente(Item item){
        if (!unidadeMedidaManager.findById(item.getUnidadeMedida().getId()).isPresent())
            throw new DependencyNotFoundException("Não foi localizada a unidade de medida informada");

    }

    private void verificaItemJaCadastrado(Long id){
        if(!itemRepository.findById(id).isPresent())
            throw new ResourceNotFoundException("Item não encontrado");
    }


}
