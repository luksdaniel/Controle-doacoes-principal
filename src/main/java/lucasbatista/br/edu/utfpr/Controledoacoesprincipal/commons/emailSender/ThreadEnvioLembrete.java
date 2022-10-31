package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.emailSender;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.item.Item;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.item.ItemManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.persistence.item.ItemService;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.doador.Doador;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.doador.DoadorManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.lembreteDoacao.LembreteDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.lembreteDoacao.LembreteDoacaoService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
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

    @Autowired
    ItemManager itemManager;

    @Autowired
    ItemService itemService;

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

    @Scheduled(fixedDelay = DIA)
    public void enviaAvisoFaltaItemDiariamente() {
        List<Item> itens = itemManager.findAllItem();
        List<Doador> doadores = doadorManager.retornaDoadoresQueRecebemEmails();
        StringBuilder conteudoEmail = new StringBuilder("Olá, o seguinte item está em falta nos nossos estoques, precisamos de sua ajuda! \n");

        if (doadores.isEmpty() || itens.isEmpty())
            return;

        for(Item itemAtual: itens){

            if(itemAtual.getUltimoEnvioEmail() == null && itemAtual.getQuantidadeEstoque() <= itemAtual.getQuantidadeMinima()) {
                conteudoEmail.append(itemAtual.getDescricao()).append("; \n");
                itemAtual.setUltimoEnvioEmail(LocalDate.now());
                itemService.updateItem(itemAtual);
            }else if (itemAtual.getQuantidadeEstoque() <= itemAtual.getQuantidadeMinima() &&
                    (itemAtual.getUltimoEnvioEmail().plusMonths(1).isBefore(LocalDate.now()) ||
                    itemAtual.getUltimoEnvioEmail().plusMonths(1).isEqual(LocalDate.now()))) {
                conteudoEmail.append(itemAtual.getDescricao()).append("; \n");
                itemAtual.setUltimoEnvioEmail(LocalDate.now());
                itemService.updateItem(itemAtual);
            }
        }

        try {

            for (Doador doadorAtual : doadores) {
                if (!doadorAtual.getEmail().isEmpty()) {
                    emailService.enviar(doadorAtual.getEmail(),
                            "Aviso de falta de itens na prefeitura",
                            conteudoEmail.toString());
                }
            }

        }catch (MailAuthenticationException ex){
            return;
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
