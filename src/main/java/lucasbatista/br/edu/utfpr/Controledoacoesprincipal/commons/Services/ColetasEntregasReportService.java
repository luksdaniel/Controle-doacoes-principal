package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.Services;


import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import jakarta.servlet.http.HttpServletResponse;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.dto.ColetaEntregaDto;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.instituicao.Instituicao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.instituicao.InstituicaoManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.persistence.dadosRelatorio.DadosRelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ColetasEntregasReportService {

    @Autowired
    InstituicaoManager instituicaoManager;

    @Autowired
    DadosRelatorioService dadosRelatorioService;

    public void export(HttpServletResponse response) throws IOException {

        Color color;
        boolean cor = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.TIMES_BOLD);
        fontTitle.setSize(18);

        Font fontContent = FontFactory.getFont(FontFactory.TIMES);
        fontContent.setSize(11);

        Optional<Instituicao> instituicao = instituicaoManager.find();
        Paragraph paragraph = new Paragraph("Relatório de Doações - "+instituicao.get().getNomeFantasia(), fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(paragraph);
        document.add(new Paragraph(" "));

        float[] columnDefinitionSize = { 13F, 17F, 30F, 30F, 10F };

        PdfPTable table = new PdfPTable(columnDefinitionSize);
        table.getDefaultCell().setBorder(0);
        table.setTotalWidth(document.getPageSize().getWidth()-50);
        table.setLockedWidth(true);

        table.addCell(cellCabecalho("Data"));
        table.addCell(cellCabecalho("Documento"));
        table.addCell(cellCabecalho("Doador/Beneficiário"));
        table.addCell(cellCabecalho("Descrição do item"));
        table.addCell(cellCabecalho("QTDe"));

        List<ColetaEntregaDto> dados = dadosRelatorioService.getDadosRelatorioColetaEntrega();
        for(ColetaEntregaDto dadoAtual: dados){
            if (cor)
                color  = Color.WHITE;
            else
                color  = Color.LIGHT_GRAY;

            String dataFormatada = dadoAtual.getData().format(formatter);
            table.addCell(cellContent(String.valueOf(dataFormatada), color));

            table.addCell(cellContent(String.valueOf(dadoAtual.getTipoDocumento()), color));

            table.addCell(cellContent(String.valueOf(dadoAtual.getDoadorBeneficiario()), color));

            table.addCell(cellContent(String.valueOf(dadoAtual.getDescricaoItem()), color));

            table.addCell(cellContent(String.valueOf(dadoAtual.getQuantidadeMovimentada()), color));

            cor = !cor;
        }

        document.add(table);
        document.close();
    }

    private PdfPCell cellCabecalho(String title){
        Font fonte = FontFactory.getFont(FontFactory.TIMES_BOLD);
        fonte.setSize(12);
        Paragraph cabecalho = new Paragraph(title,fonte);
        PdfPCell cell = new PdfPCell(cabecalho);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    private PdfPCell cellContent(String text, Color cor){
        Font fonte = FontFactory.getFont(FontFactory.TIMES);
        fonte.setSize(11);

        Paragraph pContent = new Paragraph(text, fonte);
        PdfPCell cellContent = new PdfPCell(pContent);

        cellContent.setBackgroundColor(cor);
        cellContent.setBorder(PdfPCell.NO_BORDER);
        return cellContent;
    }
}

