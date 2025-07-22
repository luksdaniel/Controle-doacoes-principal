package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.service.unidadeMedida;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.UnidadeMedida;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.repository.UnidadeMedidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class UnidadeMedidaService implements UnidadeMedidaServiceBase {

    UnidadeMedidaRepository unidadeMedidaRepository;

    @Autowired
    public UnidadeMedidaService(UnidadeMedidaRepository unidadeMedidaRepository) {
        this.unidadeMedidaRepository = unidadeMedidaRepository;
    }

    @Override
    public List<UnidadeMedida> findAllUnidadeMedida() {
        List<UnidadeMedida> unidadeMedidaList = unidadeMedidaRepository.findAll();
        if(unidadeMedidaList.isEmpty()){
            throw new ResourceNotFoundException("Unidade de medida não encontrada");
        }else{
            return unidadeMedidaList;
        }
    }

    @Override
    public Optional<UnidadeMedida> findById(Long id) {
        Optional<UnidadeMedida> un = unidadeMedidaRepository.findById(id);
        if(!un.isPresent()){
            throw new ResourceNotFoundException("Unidade de medida não encontrada");
        }else{
            return un;
        }
    }

    @Override
    public UnidadeMedida saveUnidadeMedida(UnidadeMedida unidadeMedida) {
        setaAtributosIniciais(unidadeMedida);
        return unidadeMedidaRepository.save(unidadeMedida);
    }

    @Override
    public UnidadeMedida updaUnidadeMedida(UnidadeMedida unidadeMedida) {
        verificaUnidadeMedidaJaCadastrada(unidadeMedida.getId());
        return (unidadeMedidaRepository.save(unidadeMedida));
    }

    @Override
    public void deleteUnidadeMedida(Long id) {
        verificaUnidadeMedidaJaCadastrada(id);
        unidadeMedidaRepository.deleteById(id);
    }

    private void verificaUnidadeMedidaJaCadastrada(Long id){
        if(!unidadeMedidaRepository.findById(id).isPresent())
            throw new ResourceNotFoundException("Unidade de medida não encontrada");
    }

    private void setaAtributosIniciais(UnidadeMedida unidadeMedida){
        unidadeMedida.setEstaCancelado(false);
    }
}
