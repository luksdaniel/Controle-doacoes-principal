package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.persistence.ajusteManualEstoque;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.entity.AjusteManualEstoque;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.repository.AjusteManualEstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AjusteManualEstImpService implements AjusteManualEstService{

    @Autowired
    AjusteManualEstoqueRepository ajusteManualEstoqueRepository;

    @Override
    public List<AjusteManualEstoque> findAllEndereco() {
        return ajusteManualEstoqueRepository.findAll();
    }

    @Override
    public Optional<AjusteManualEstoque> findById(Long id) {
        return ajusteManualEstoqueRepository.findById(id);
    }

    @Override
    public AjusteManualEstoque saveAjusteManualEstoque(AjusteManualEstoque ajusteManualEstoque) {
        return ajusteManualEstoqueRepository.save(ajusteManualEstoque);
    }

    @Override
    public AjusteManualEstoque updateAjusteManualEstoque(AjusteManualEstoque ajusteManualEstoque) {
        return ajusteManualEstoqueRepository.save(ajusteManualEstoque);
    }

    @Override
    public void deleteAjusteManualEstoque(Long id) {
        ajusteManualEstoqueRepository.deleteById(id);
    }
}
