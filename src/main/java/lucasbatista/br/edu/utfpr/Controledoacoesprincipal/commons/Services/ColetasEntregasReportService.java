package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.Services;


import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.dto.ColetaEntregaDto;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.instituicao.Instituicao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.instituicao.InstituicaoManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.persistence.dadosRelatorio.DadosRelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ColetasEntregasReportService {

    @Autowired
    InstituicaoManager instituicaoManager;

    @Autowired
    DadosRelatorioService dadosRelatorioService;

    public void export(HttpServletResponse response) throws IOException {

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.TIMES_BOLD);
        fontTitle.setSize(18);

        Optional<Instituicao> instituicao = instituicaoManager.find();
        Paragraph paragraph = new Paragraph("Relatório de Doações - "+instituicao.get().getNomeFantasia(), fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(paragraph);
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(5);
        table.getDefaultCell().setBorder(0);
        table.setTotalWidth(document.getPageSize().getWidth() - 20);

        PdfPCell col1 = new PdfPCell(new Paragraph("Data"));
        PdfPCell col2 = new PdfPCell(new Paragraph("Tipo de documento"));
        PdfPCell col3 = new PdfPCell(new Paragraph("Doador/Beneficiário"));
        PdfPCell col4 = new PdfPCell(new Paragraph("Descrição do item"));
        PdfPCell col5 = new PdfPCell(new Paragraph("Quantidade"));

        table.addCell(col1);
        table.addCell(col2);
        table.addCell(col3);
        table.addCell(col4);
        table.addCell(col5);

        List<ColetaEntregaDto> dados = dadosRelatorioService.getDadosRelatorioColetaEntrega();
        for(ColetaEntregaDto dadoAtual: dados){
            table.addCell(String.valueOf(dadoAtual.getData()));
            table.addCell(String.valueOf(dadoAtual.getTipoDocumento()));
            table.addCell(String.valueOf(dadoAtual.getDoadorBeneficiario()));
            table.addCell(String.valueOf(dadoAtual.getDescricaoItem()));
            table.addCell(String.valueOf(dadoAtual.getQuantidadeMovimentada()));
        }

        document.add(table);
        document.close();
    }

}

