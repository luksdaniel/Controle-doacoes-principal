package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.service.beneficiario;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.Beneficiario;

import java.util.List;
import java.util.Optional;

public interface BeneficiarioServiceBase {

    List<Beneficiario> findAllBeneficiario();
    Optional<Beneficiario> findById(Long id);
    Beneficiario saveBeneficiario(Beneficiario beneficiario);
    Beneficiario updateBeneficiario(Beneficiario beneficiario);
    Beneficiario cancelBeneficiario(Long id);
    Beneficiario uncancelBeneficiario(Long id);
    void deleteBeneficiario(Long id);

}
