package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.unidadeMedida;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceCreateErrorException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.persistence.unidadeMedida.UnidadeMedidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class UnidadeMedidaManagerImp implements UnidadeMedidaManager {

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
        if(!un.isPresent()){
            throw new ResourceNotFoundException("Unidade de medida não encontrada");
        }else{
            return un;
        }
    }

    @Override
    public UnidadeMedida saveUnidadeMedida(UnidadeMedida unidadeMedida) {
        setaAtributosIniciais(unidadeMedida);
        return unidadeMedidaService.saveUnidadeMedida(unidadeMedida);
    }

    @Override
    public UnidadeMedida updaUnidadeMedida(UnidadeMedida unidadeMedida) {
        verificaUnidadeMedidaJaCadastrada(unidadeMedida.getId());
        return (unidadeMedidaService.updaUnidadeMedida(unidadeMedida));
    }

    @Override
    public void deleteUnidadeMedida(Long id) {
        verificaUnidadeMedidaJaCadastrada(id);
        unidadeMedidaService.deleteUnidadeMedida(id);
    }

    private void verificaUnidadeMedidaJaCadastrada(Long id){
        if(!unidadeMedidaService.findById(id).isPresent())
            throw new ResourceNotFoundException("Unidade de medida não encontrada");
    }

    private void setaAtributosIniciais(UnidadeMedida unidadeMedida){
        unidadeMedida.setEstaCancelado(false);
    }
}
