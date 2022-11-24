package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.doador;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.doador.Doador;

import java.util.List;
import java.util.Optional;

public interface DoadorService {

    List<Doador> findAllDoador();

    List<Doador> findDoadorsByRecebeEmailsTrue();

    Optional<Doador> findById(Long id);

    Optional<Doador> findByEmail(String email);

    Doador saveDoador(Doador doador);

    Doador updateDoador(Doador doador);

    void deleteDoador(Long id);

}
