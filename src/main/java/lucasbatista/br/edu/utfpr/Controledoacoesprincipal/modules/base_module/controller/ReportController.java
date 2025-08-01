package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.controller;

import jakarta.servlet.http.HttpServletResponse;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.Services.ColetasEntregasReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/relatorios")
public class ReportController {

    ColetasEntregasReportService coletasEntregasReportService;

    @Autowired
    public ReportController(ColetasEntregasReportService coletasEntregasReportService) {
        this.coletasEntregasReportService = coletasEntregasReportService;
    }

    @GetMapping("entregas-coletas")
    public void relatorioColetasEntregas(HttpServletResponse response) throws IOException {
       response.setContentType("application/pdf");
       LocalDate date = LocalDate.now();

       String headerKey = "Content-Disposition";
       String headerValue = "attachment; filename=Relatorio_" + String.valueOf(date) + ".pdf";

       response.setHeader(headerKey, headerValue);
       coletasEntregasReportService.export(response);
    }
}
