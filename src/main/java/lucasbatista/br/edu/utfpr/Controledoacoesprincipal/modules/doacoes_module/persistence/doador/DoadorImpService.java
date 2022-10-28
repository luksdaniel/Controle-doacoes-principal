package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.doador;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.doador.Doador;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.repository.DoadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoadorImpService implements DoadorService{

    @Autowired
    DoadorRepository doadorRepository;

    @Override
    public List<Doador> findAllDoador() {
        return doadorRepository.findAll();
    }

    @Override
    public List<Doador> findDoadorsByRecebeEmailsTrue() {
        return doadorRepository.findDoadorsByRecebeEmailsTrue();
    }

    @Override
    public Optional<Doador> findById(Long id) {
        return doadorRepository.findById(id);
    }

    @Override
    public Doador saveDoador(Doador doador) {
        return doadorRepository.save(doador);
    }

    @Override
    public Doador updateDoador(Doador doador) {
        return doadorRepository.save(doador);
    }

    @Override
    public void deleteDoador(Long id) {
        doadorRepository.deleteById(id);
    }
}
