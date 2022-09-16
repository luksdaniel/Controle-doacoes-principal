package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.ajusteManualEstoque;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceCreateErrorException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.controller.AjusteManualEstoqueController;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.item.ItemManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.usuario.UsuarioManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.persistence.ajusteManualEstoque.AjusteManualEstService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
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
        return/* List<AjusteManualEstoque> ajusteList =*/ ajusteManualEstService.findByItemId(itemId);
    }

    @Override
    public AjusteManualEstoque saveAjusteManual(AjusteManualEstoque ajusteManualEstoque) {
        setaAtributosIniciais(ajusteManualEstoque);

        ajusteManualEstoque.setItem(itemManager.findById(ajusteManualEstoque.getItem().getId()).get());
        ajusteManualEstoque.setUsuarioResponsavel(usuarioManager.findById(ajusteManualEstoque.getUsuarioResponsavel().getId()).get());

        AjusteManualEstoque ajusteInterno = ajusteManualEstService.saveAjusteManualEstoque(ajusteManualEstoque);
        if(ajusteInterno == null){
            throw new ResourceCreateErrorException("Não foi possível criar o ajuste manual de estoque!");
        }else {
            return ajusteInterno;
        }
    }

    @Override
    public AjusteManualEstoque cancelaAjusteManual(long id) {
        return null;
    }

    private void setaAtributosIniciais(AjusteManualEstoque ajuste){
        ajuste.setDataAjuste(LocalDate.now());
        ajuste.setEstaCancelada(false);
    }
}
