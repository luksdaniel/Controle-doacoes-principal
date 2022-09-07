package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.doador;

import java.util.List;
import java.util.Optional;

public interface DoadorManager {

    List<Doador> findAllDoador();

    Optional<Doador> findById(Long id);

    Doador saveDoador(Doador doador);

    Doador updateDoador(Doador doador);

    void deleteDoador(Long id);

}
