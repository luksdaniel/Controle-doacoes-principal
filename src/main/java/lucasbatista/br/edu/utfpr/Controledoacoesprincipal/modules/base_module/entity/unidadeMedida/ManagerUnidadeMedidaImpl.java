package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.unidadeMedida;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceCreateError;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.controller.UnidadeMedidaController;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.persistence.unidadeMedida.UnidadeMedidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ManagerUnidadeMedidaImpl implements ManagerUnidadeMedida{

    @Autowired
    UnidadeMedidaService unidadeMedidaService;

    @Override
    public List<UnidadeMedida> findAllUnidadeMedida() {
        List<UnidadeMedida> unidadeMedidaList = unidadeMedidaService.findAllUnidadeMedida();
        if(unidadeMedidaList.isEmpty()){
            throw new ResourceNotFoundException("Unidade de medida não encontrada");
        }else{
            return unidadeMedidaList;
        }
    }

    @Override
    public Optional<UnidadeMedida> findById(Long id) {
        Optional<UnidadeMedida> un = unidadeMedidaService.findById(id);
        if(un.isEmpty()){
            throw new ResourceNotFoundException("Unidade de medida não encontrada");
        }else{
            return un;
        }
    }

    @Override
    public UnidadeMedida saveUnidadeMedida(UnidadeMedida unidadeMedida) {
        UnidadeMedida un = unidadeMedidaService.saveUnidadeMedida(unidadeMedida);
        if(unidadeMedida == null){
            throw new ResourceCreateError("Não foi possível criar a unidade de medida");
        }else{
            return un;
        }
    }

    @Override
    public UnidadeMedida updaUnidadeMedida(UnidadeMedida unidadeMedida) {
        if(unidadeMedidaService.findById(unidadeMedida.getId()).isEmpty()){
            throw new ResourceNotFoundException("Unidade de medida não encontrada");
        } else {
            UnidadeMedida un = (unidadeMedidaService.updaUnidadeMedida(unidadeMedida));
            return un;
        }
    }

    @Override
    public void deleteUnidadeMedida(Long id) {
        if(unidadeMedidaService.findById(id).isEmpty()){
            throw new ResourceNotFoundException("Unidade de medida não encontrada");
        }else {
            unidadeMedidaService.deleteUnidadeMedida(id);
        }
    }
}
