package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.beneficiario;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.beneficiario.Beneficiario;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.repository.BeneficiarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BeneficiarioImplService implements BeneficiarioService {

    @Autowired
    BeneficiarioRepository beneficiarioRepository;

    @Override
    public List<Beneficiario> findAllBeneficiario() {
        return beneficiarioRepository.findAll();
    }

    @Override
    public Optional<Beneficiario> findById(Long id) {
        return beneficiarioRepository.findById(id);
    }

    @Override
    public Beneficiario saveBeneficiario(Beneficiario beneficiario) {
        return beneficiarioRepository.save(beneficiario);
    }

    @Override
    public Beneficiario updateBeneficiario(Beneficiario beneficiario) {
        return beneficiarioRepository.save(beneficiario);
    }

    @Override
    public void deleteBeneficiario(Long id) {
        beneficiarioRepository.deleteById(id);
    }
}
