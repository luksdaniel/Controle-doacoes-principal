package lucasbatista.br.edu.utfpr.Controledoacoesprincipal;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.initializeResource.InictializeResource;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.item.Item;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.persistence.dadosRelatorio.DadosRelatorioServiceImp;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.Optional;

@SpringBootApplication
public class ControleDoacoesPrincipalApplication {

	@Autowired
	InictializeResource inictializeResource;

	@Autowired
	DadosRelatorioServiceImp dados;

	public static void main(String[] args) {
		SpringApplication.run(ControleDoacoesPrincipalApplication.class, args);
	}

	@PostConstruct
	public void initializeResourse(){
		inictializeResource.createDefaultIntituicao();
		dados.findAllMovimentacoes(4);
	}


}
