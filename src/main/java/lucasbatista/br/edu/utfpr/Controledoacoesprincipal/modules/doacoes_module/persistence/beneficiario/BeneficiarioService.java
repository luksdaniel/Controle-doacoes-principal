package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.beneficiario;



import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.Beneficiario;

import java.util.List;
import java.util.Optional;

public interface BeneficiarioService {

    List<Beneficiario> findAllBeneficiario();

    Optional<Beneficiario> findById(Long id);

    Beneficiario saveBeneficiario(Beneficiario beneficiario);

    Beneficiario updateBeneficiario(Beneficiario beneficiario);

    void deleteBeneficiario(Long id);

}
