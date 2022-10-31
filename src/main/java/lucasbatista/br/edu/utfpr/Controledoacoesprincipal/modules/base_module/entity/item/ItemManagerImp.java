package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.item;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.emailSender.EmailService;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.BusinessException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.DependencyNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceCreateErrorException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.unidadeMedida.UnidadeMedidaManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.persistence.item.ItemService;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.doador.Doador;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.doador.DoadorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class ItemManagerImp implements ItemManager {

    @Autowired
    ItemService itemService;

    @Autowired
    UnidadeMedidaManager unidadeMedidaManager;

    @Autowired
    EmailService emailService;

    @Autowired
    DoadorManager doadorManager;

    @Override
    public List<Item> findAllItem() {
        List<Item> itemList = itemService.findAllItem();
        if(itemList.isEmpty()){
            throw new ResourceNotFoundException("Itens não encontrados");
        }else{
            return itemList;
        }
    }

    @Override
    public Optional<Item> findById(Long id) {
        Optional<Item> item = itemService.findById(id);
        if(item.isEmpty()){
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
        Item itemInterno = itemService.saveItem(item);

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
        return (itemService.updateItem(item));
    }

    @Override
    public Item cancelItem(Long id) {
        Optional<Item> item = itemService.findById(id);

        if (item.get().isEstaCancelado())
            throw new BusinessException("Item já cancelado");

        item.get().setEstaCancelado(true);

        return (itemService.updateItem(item.get()));
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
        if (unidadeMedidaManager.findById(item.getUnidadeMedida().getId()).isEmpty())
            throw new DependencyNotFoundException("Não foi localizada a unidade de medida informada");

    }

    private void verificaItemJaCadastrado(Long id){
        if(itemService.findById(id).isEmpty())
            throw new ResourceNotFoundException("Item não encontrado");
    }


}
