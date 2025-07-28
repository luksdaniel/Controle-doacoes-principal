package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.service.lembreteDoacao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.LembreteDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.repository.LembreteDoacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class LembreteDoacaoService implements LembreteDoacaoServiceBase {

    LembreteDoacaoRepository lembreteDoacaoRepository;

    @Autowired
    public LembreteDoacaoService(LembreteDoacaoRepository lembreteDoacaoRepository) {
        this.lembreteDoacaoRepository = lembreteDoacaoRepository;
    }

    @Override
    public List<LembreteDoacao> findAllLembreteDoacao() {
        List<LembreteDoacao> lembreteList = lembreteDoacaoRepository.findAll();

        if (lembreteList.isEmpty()){
            throw new ResourceNotFoundException("Lembretes de doação não encontrados");
        }else {
            return lembreteList;
        }
    }

    @Override
    public Optional<LembreteDoacao> findById(long id) {
        Optional<LembreteDoacao> lembrete = lembreteDoacaoRepository.findById(id);

        if (!lembrete.isPresent()){
            throw new ResourceNotFoundException("Lembrete de doação não encontrado");
        }else {
            return lembrete;
        }
    }

    @Override
    public LembreteDoacao saveLembreteDoacao(LembreteDoacao lembreteDoacao) {
        setaAtributosIniciais(lembreteDoacao);

        return lembreteDoacaoRepository.save(lembreteDoacao);
    }

    @Override
    public LembreteDoacao updateLembreteDoacao(LembreteDoacao lembreteDoacao) {
        verificaLembretejaCadastrado(lembreteDoacao.getId());
        setaAtributosIniciais(lembreteDoacao);

        return lembreteDoacaoRepository.save(lembreteDoacao);
    }

    @Override
    public void deleteLembreteDoacao(long id) {
        verificaLembretejaCadastrado(id);
        lembreteDoacaoRepository.deleteById(id);
    }

    private void setaAtributosIniciais(LembreteDoacao lembreteDoacao){
        lembreteDoacao.setDataAgendamento(LocalDate.now());
    }

    private void verificaLembretejaCadastrado(Long id){
        if(!lembreteDoacaoRepository.findById(id).isPresent())
            throw new ResourceNotFoundException("Lembrete não encontrado");
    }
}
