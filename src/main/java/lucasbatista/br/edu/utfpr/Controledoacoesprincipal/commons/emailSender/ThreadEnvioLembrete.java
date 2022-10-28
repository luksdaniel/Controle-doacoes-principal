package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.emailSender;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.doador.Doador;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.doador.DoadorManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.lembreteDoacao.LembreteDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.lembreteDoacao.LembreteDoacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@EnableScheduling
public class ThreadEnvioLembrete {

    @Autowired
    LembreteDoacaoService lembreteService;

    @Autowired
    EmailService emailService;

    @Autowired
    DoadorManager doadorManager;

    private final long SEGUNDO = 1000;
    private final long MINUTO = SEGUNDO * 60;
    private final long HORA = MINUTO * 60;
    private final long DIA = HORA * 24;

    @Scheduled(fixedDelay = DIA)
    public void enviaLembretesDiariament() {

        List<LembreteDoacao> lembretes = lembreteService.findAllLembreteDoacao();
        List<Doador> doadores = doadorManager.retornaDoadoresQueRecebemEmails();

        if(doadores.isEmpty() || lembretes.isEmpty())
            return;

        for (LembreteDoacao lembreteAtual: lembretes){
            if(lembreteAtual.isRepetirTodoMes()){

                if ((lembreteAtual.getDataAgendamento().getDayOfMonth() == LocalDate.now().getDayOfMonth()) &&
                        lembreteAtual.getDataUltimoEnvio() == null ||
                        lembreteAtual.getDataUltimoEnvio().plusMonths(1).isBefore(LocalDate.now()) ||
                        lembreteAtual.getDataUltimoEnvio().plusMonths(1).isEqual(LocalDate.now())){
                    enviaEmailParaDoadores(doadores, lembreteAtual);
                }
            }else if (lembreteAtual.getDataUltimoEnvio() == null &&
                    lembreteAtual.getDataAgendamento().isEqual(LocalDate.now())){
                enviaEmailParaDoadores(doadores, lembreteAtual);
            }

        }
    }

    private void enviaEmailParaDoadores(List<Doador> doadores, LembreteDoacao lembrete){
        for (Doador doadorAtual: doadores){
            if (!doadorAtual.getEmail().isEmpty()) {
                emailService.enviar(doadorAtual.getEmail(),"Lembrete de doação para prefeitura",lembrete.getMensagem());

                lembrete.setDataUltimoEnvio(LocalDate.now());
                lembreteService.updateLembreteDoacao(lembrete);
            }
        }

    }

}
