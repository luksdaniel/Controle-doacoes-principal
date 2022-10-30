package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.ajusteManualEstoque;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.BusinessException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceCreateErrorException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.item.ItemManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.entity.usuario.UsuarioManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.persistence.ajusteManualEstoque.AjusteManualEstService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class AjusteManualEstoqueManagerImp implements AjusteManualEstoqueManager{

    @Autowired
    AjusteManualEstService ajusteManualEstService;

    @Autowired
    ItemManager itemManager;

    @Autowired
    UsuarioManager usuarioManager;

    @Override
    public List<AjusteManualEstoque> findAllAjusteManual() {
        List<AjusteManualEstoque> ajusteList = ajusteManualEstService.findAllAjusteManualEst();

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

        AjusteManualEstoque ajusteInterno = ajusteManualEstService.saveAjusteManualEstoque(ajusteManualEstoque);
        if(ajusteInterno == null){
            throw new ResourceCreateErrorException("Não foi possível criar o ajuste manual de estoque!");
        }else {
            return ajusteInterno;
        }
    }

    @Override
    public AjusteManualEstoque cancelaAjusteManual(long id) {
        Optional<AjusteManualEstoque> ajusteManualEstoque = ajusteManualEstService.findById(id);

        if (ajusteManualEstoque.isEmpty())
            throw new ResourceNotFoundException("Ajuste de estoque não encontrado");
        else {
            if (ajusteManualEstoque.get().isEstaCancelada())
                throw new BusinessException("O ajuste manual já está cancelado!");

            itemManager.validaAndMovimentaEstoque(ajusteManualEstoque.get().getItem(), -ajusteManualEstoque.get().getQuantidadeMovimentada());
            ajusteManualEstoque.get().setEstaCancelada(true);
        }
        return ajusteManualEstService.updateAjusteManualEstoque(ajusteManualEstoque.get());
    }

    private void setaAtributosIniciais(AjusteManualEstoque ajuste){
        ajuste.setDataAjuste(LocalDate.now());
        ajuste.setEstaCancelada(false);

        ajuste.setItem(itemManager.findById(ajuste.getItem().getId()).get());
        ajuste.setUsuarioResponsavel(usuarioManager.findById(ajuste.getUsuarioResponsavel().getId()).get());
    }
}
