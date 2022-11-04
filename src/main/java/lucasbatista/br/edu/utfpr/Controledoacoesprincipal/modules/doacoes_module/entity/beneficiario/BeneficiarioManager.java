package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.beneficiario;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.item.Item;

import java.util.List;
import java.util.Optional;

public interface BeneficiarioManager {

    List<Beneficiario> findAllBeneficiario();
    Optional<Beneficiario> findById(Long id);
    Beneficiario saveBeneficiario(Beneficiario beneficiario);
    Beneficiario updateBeneficiario(Beneficiario beneficiario);
    Beneficiario cancelBeneficiario(Long id);
    Beneficiario uncancelBeneficiario(Long id);
    void deleteBeneficiario(Long id);

}
