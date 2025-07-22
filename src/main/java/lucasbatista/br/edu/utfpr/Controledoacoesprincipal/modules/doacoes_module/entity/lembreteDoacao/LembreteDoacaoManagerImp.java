package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.lembreteDoacao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceCreateErrorException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.lembreteDoacao.LembreteDoacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class LembreteDoacaoManagerImp implements LembreteDoacaoManager{

    @Autowired
    LembreteDoacaoService lembreteDoacaoService;

    @Override
    public List<LembreteDoacao> findAllLembreteDoacao() {
        List<LembreteDoacao> lembreteList = lembreteDoacaoService.findAllLembreteDoacao();

        if (lembreteList.isEmpty()){
            throw new ResourceNotFoundException("Lembretes de doação não encontrados");
        }else {
            return lembreteList;
        }
    }

    @Override
    public Optional<LembreteDoacao> findById(long id) {
        Optional<LembreteDoacao> lembrete = lembreteDoacaoService.findById(id);

        if (!lembrete.isPresent()){
            throw new ResourceNotFoundException("Lembrete de doação não encontrado");
        }else {
            return lembrete;
        }
    }

    @Override
    public LembreteDoacao saveLembreteDoacao(LembreteDoacao lembreteDoacao) {
        setaAtributosIniciais(lembreteDoacao);

        LembreteDoacao lembreteInterno = lembreteDoacaoService.saveLembreteDoacao(lembreteDoacao);
        if(lembreteInterno == null){
            throw new ResourceCreateErrorException("Não foi possível gravar o lembrete de doação!");
        }else {
            return lembreteInterno;
        }
    }

    @Override
    public LembreteDoacao updateLembreteDoacao(LembreteDoacao lembreteDoacao) {
        verificaLembretejaCadastrado(lembreteDoacao.getId());
        setaAtributosIniciais(lembreteDoacao);

        return lembreteDoacaoService.updateLembreteDoacao(lembreteDoacao);
    }

    @Override
    public void deleteLembreteDoacao(long id) {
        verificaLembretejaCadastrado(id);
        lembreteDoacaoService.deleteLembreteDoacao(id);
    }

    private void setaAtributosIniciais(LembreteDoacao lembreteDoacao){
        lembreteDoacao.setDataAgendamento(LocalDate.now());
    }

    private void verificaLembretejaCadastrado(Long id){
        if(!lembreteDoacaoService.findById(id).isPresent())
            throw new ResourceNotFoundException("Lembrete não encontrado");
    }
}
