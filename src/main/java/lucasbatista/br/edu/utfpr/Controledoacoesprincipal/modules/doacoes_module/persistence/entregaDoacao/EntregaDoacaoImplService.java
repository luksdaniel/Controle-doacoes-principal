package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.entregaDoacao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.entregaDoacao.EntregaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.repository.EntregaDoacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntregaDoacaoImplService implements EntregaDoacaoService{

    @Autowired
    EntregaDoacaoRepository entregaDoacaoRepository;

    @Override
    public List<EntregaDoacao> findAllEntregaDoacao() {
        return entregaDoacaoRepository.findAll();
    }

    @Override
    public Optional<EntregaDoacao> findById(Long id) {
        return entregaDoacaoRepository.findById(id);
    }

    @Override
    public List<EntregaDoacao> findByBeneficiarioId(Long id) {
        return null;
    }

    @Override
    public List<EntregaDoacao> findByUsuarioRegistroId(Long id) {
        return null;
    }

    @Override
    public EntregaDoacao cancelaEntregaDoacao(EntregaDoacao entregaDoacao) {
        return null;
    }

    @Override
    public EntregaDoacao saveEntregaDoacao(EntregaDoacao entregaDoacao) {
        return entregaDoacaoRepository.save(entregaDoacao);
    }

    @Override
    public EntregaDoacao updateEntregaDoacao(EntregaDoacao entregaDoacao) {
        return entregaDoacaoRepository.save(entregaDoacao);
    }

    @Override
    public void deleteEntregaDoacao(Long id) {
        entregaDoacaoRepository.deleteById(id);
    }
}
