package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.emailSender;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.Item;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.service.item.ItemManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.repository.ItemRepository;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.Doador;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.repository.LembreteDoacaoRepository;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.service.doador.DoadorServiceBase;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.LembreteDoacao;
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

    LembreteDoacaoRepository lembreteDoacaoRepository;
    EmailService emailService;
    DoadorServiceBase doadorServiceBase;
    ItemManager itemManager;
    ItemRepository itemRepository;

    @Autowired
    public ThreadEnvioLembrete(LembreteDoacaoRepository lembreteDoacaoRepository,
                               EmailService emailService,
                               DoadorServiceBase doadorServiceBase,
                               ItemManager itemManager,
                               ItemRepository itemRepository){
        this.lembreteDoacaoRepository = lembreteDoacaoRepository;
        this.emailService = emailService;
        this.doadorServiceBase = doadorServiceBase;
        this.itemManager = itemManager;
        this.itemRepository = itemRepository;
    }

    private final long SEGUNDO = 1000;
    private final long MINUTO = SEGUNDO * 60;
    private final long HORA = MINUTO * 60;
    private final long DIA = HORA * 24;

    @Scheduled(fixedDelay = DIA)
    public void enviaLembretesDiariament() {

        List<LembreteDoacao> lembretes = lembreteDoacaoRepository.findAll();
        List<Doador> doadores = doadorServiceBase.retornaDoadoresQueRecebemEmails();

        if(doadores.isEmpty() || lembretes.isEmpty())
            return;

        for (LembreteDoacao lembreteAtual: lembretes){
            if(lembreteAtual.isRepetirTodoMes()){

                if (lembreteAtual.getDataUltimoEnvio() == null)
                    lembreteAtual.setDataUltimoEnvio(LocalDate.now().minusMonths(2));

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
        try {
            List<Item> itens = itemRepository.findAll();
            List<Doador> doadores = doadorServiceBase.retornaDoadoresQueRecebemEmails();
            StringBuilder conteudoEmail = new StringBuilder("Olá, o seguinte item está em falta nos nossos estoques, precisamos de sua ajuda! \n");

            if (doadores.isEmpty() || itens.isEmpty())
                return;

            for (Item itemAtual : itens) {

                if (itemAtual.getUltimoEnvioEmail() == null && itemAtual.getQuantidadeEstoque() <= itemAtual.getQuantidadeMinima()) {
                    conteudoEmail.append(itemAtual.getDescricao()).append("; \n");
                    itemAtual.setUltimoEnvioEmail(LocalDate.now());
                    itemRepository.save(itemAtual);
                } else {
                    if (itemAtual.getQuantidadeEstoque() <= itemAtual.getQuantidadeMinima()) {
                        assert itemAtual.getUltimoEnvioEmail() != null;
                        if (itemAtual.getUltimoEnvioEmail().plusMonths(1).isBefore(LocalDate.now()) ||
                                itemAtual.getUltimoEnvioEmail().plusMonths(1).isEqual(LocalDate.now())) {
                            conteudoEmail.append(itemAtual.getDescricao()).append("; \n");
                            itemAtual.setUltimoEnvioEmail(LocalDate.now());
                            itemRepository.save(itemAtual);
                        }
                    }
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

            } catch (MailAuthenticationException ex) {
                return;
            }
        }catch (Exception ex){
            return;
        }
    }

    private void enviaEmailParaDoadores(List<Doador> doadores, LembreteDoacao lembrete){
        for (Doador doadorAtual: doadores){
            if (!doadorAtual.getEmail().isEmpty()) {
                emailService.enviar(doadorAtual.getEmail(),"Lembrete de doação para prefeitura",lembrete.getMensagem());

                lembrete.setDataUltimoEnvio(LocalDate.now());
                lembreteDoacaoRepository.save(lembrete);
            }
        }

    }

}
