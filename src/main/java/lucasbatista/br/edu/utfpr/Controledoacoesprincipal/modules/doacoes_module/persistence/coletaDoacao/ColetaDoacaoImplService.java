package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.coletaDoacao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.coletaDoacao.ColetaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.repository.ColetaDoacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ColetaDoacaoImplService implements ColetaDoacaoService{

    @Autowired
    ColetaDoacaoRepository coletaDoacaoRepository;

    @Override
    public List<ColetaDoacao> findAllColetaDoacao() {
        return coletaDoacaoRepository.findAll();
    }

    @Override
    public List<ColetaDoacao> findByIdDoador(long id) {
        return coletaDoacaoRepository.findColetaDoacaoByDoadorId(id);
    }

    @Override
    public List<ColetaDoacao> findByIdUsuarioRegistro(long id) {
        return coletaDoacaoRepository.findColetaDoacaoByUsuarioRegistroId(id);
    }

    @Override
    public List<ColetaDoacao> findByDataBetween(LocalDate dataInicio, LocalDate dataFim) {
        return coletaDoacaoRepository.findByDataEfetivacaoBetween(dataInicio, dataFim);
    }

    @Override
    public Optional<ColetaDoacao> findById(Long id) {
        return coletaDoacaoRepository.findById(id);
    }

    @Override
    public ColetaDoacao saveColetaDoacao(ColetaDoacao coletaDoacao) {
        return coletaDoacaoRepository.save(coletaDoacao);
    }

    @Override
    public ColetaDoacao updateColetaDoacao(ColetaDoacao coletaDoacao) {
        return coletaDoacaoRepository.save(coletaDoacao);
    }

    @Override
    public void deleteColetaDoacao(Long id) {
        coletaDoacaoRepository.deleteById(id);
    }
}
