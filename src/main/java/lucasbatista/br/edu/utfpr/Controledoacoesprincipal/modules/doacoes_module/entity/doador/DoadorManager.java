package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.doador;

import java.util.List;
import java.util.Optional;

public interface DoadorManager {

    List<Doador> findAllDoador();

    List<Doador> retornaDoadoresQueRecebemEmails();

    Optional<Doador> findById(Long id);

    Doador saveDoador(Doador doador);

    Doador updateDoador(Doador doador);

    Doador cancelDoador(Long id);

    Doador uncancelDoador(Long id);

    void deleteDoador(Long id);

}
