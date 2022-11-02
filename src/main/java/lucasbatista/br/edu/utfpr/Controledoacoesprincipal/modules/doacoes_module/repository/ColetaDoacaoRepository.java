package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.repository;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.coletaDoacao.ColetaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.itemColetaDoacao.ItemColetaDoacao;
import net.bytebuddy.asm.Advice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ColetaDoacaoRepository extends JpaRepository<ColetaDoacao, Long> {

    List<ColetaDoacao> findColetaDoacaoByDoadorId(long id);

    List<ColetaDoacao> findColetaDoacaoByUsuarioRegistroId(long id);

    //List<ColetaDoacao> findByItensColeta(List<ItemColetaDoacao> itens);

    List<ColetaDoacao> findByDataEfetivacaoBetween(LocalDate dataInicio, LocalDate dataFim);

}
