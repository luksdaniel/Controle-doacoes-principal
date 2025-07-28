package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.service.ajusteManual;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.BusinessException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.AjusteManualEstoque;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.service.item.ItemManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.repository.AjusteManualEstoqueRepository;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.service.usuario.UsuarioServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class AjusteManualService implements AjusteManualServiceBase {

    AjusteManualEstoqueRepository ajusteManualEstService;
    ItemManager itemManager;
    UsuarioServiceBase usuarioServiceBase;

    @Autowired
    public AjusteManualService(AjusteManualEstoqueRepository ajusteManualEstService, ItemManager itemManager, UsuarioServiceBase usuarioServiceBase) {
        this.ajusteManualEstService = ajusteManualEstService;
        this.itemManager = itemManager;
        this.usuarioServiceBase = usuarioServiceBase;
    }

    @Override
    public List<AjusteManualEstoque> findAllAjusteManual() {
        List<AjusteManualEstoque> ajusteList = ajusteManualEstService.findAll();

        if (ajusteList.isEmpty()){
            throw new ResourceNotFoundException("Ajustes de estoque não encontrados");
        }else {
            return ajusteList;
        }
    }

    @Override
    public List<AjusteManualEstoque> findByItemId(long itemId) {
        List<AjusteManualEstoque> ajusteList = ajusteManualEstService.findByItemId(itemId);

        if (ajusteList.isEmpty()){
            throw new ResourceNotFoundException("Ajustes de estoque não encontrados");
        }else {
            return ajusteList;
        }
    }

    @Override
    public AjusteManualEstoque saveAjusteManual(AjusteManualEstoque ajusteManualEstoque) {
        setaAtributosIniciais(ajusteManualEstoque);

        itemManager.validaAndMovimentaEstoque(ajusteManualEstoque.getItem(), ajusteManualEstoque.getQuantidadeMovimentada());

        return ajusteManualEstService.save(ajusteManualEstoque);
    }

    @Override
    public AjusteManualEstoque cancelaAjusteManual(long id) {
        Optional<AjusteManualEstoque> ajusteManualEstoque = ajusteManualEstService.findById(id);

        if (!ajusteManualEstoque.isPresent())
            throw new ResourceNotFoundException("Ajuste de estoque não encontrado");
        else {
            if (ajusteManualEstoque.get().isEstaCancelada())
                throw new BusinessException("O ajuste manual já está cancelado!");

            itemManager.validaAndMovimentaEstoque(ajusteManualEstoque.get().getItem(), -ajusteManualEstoque.get().getQuantidadeMovimentada());
            ajusteManualEstoque.get().setEstaCancelada(true);
        }
        return ajusteManualEstService.save(ajusteManualEstoque.get());
    }

    private void setaAtributosIniciais(AjusteManualEstoque ajuste){
        ajuste.setDataAjuste(LocalDate.now());
        ajuste.setEstaCancelada(false);

        ajuste.setItem(itemManager.findById(ajuste.getItem().getId()).get());
        ajuste.setUsuarioResponsavel(usuarioServiceBase.findById(ajuste.getUsuarioResponsavel().getId()).get());
    }
}
