package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.doador;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.doador.DoadorService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DoadorManagerImp implements DoadorManager{

    DoadorService doadorService;

    @Override
    public List<Doador> findAllDoador() {
        return doadorService.findAllDoador();
    }

    @Override
    public Optional<Doador> findById(Long id) {
        return doadorService.findById(id);
    }

    @Override
    public Doador saveDoador(Doador doador) {
        return doadorService.saveDoador(doador);
    }

    @Override
    public Doador updateDoador(Doador doador) {
        return doadorService.updateDoador(doador);
    }

    @Override
    public void deleteDoador(Long id) {
        doadorService.deleteDoador(id);
    }
}
