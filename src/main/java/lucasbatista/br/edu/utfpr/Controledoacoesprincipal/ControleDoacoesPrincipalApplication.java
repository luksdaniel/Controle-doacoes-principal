package lucasbatista.br.edu.utfpr.Controledoacoesprincipal;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.emailSender.EmailService;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.initializeResource.InictializeResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class ControleDoacoesPrincipalApplication {

	@Autowired
	InictializeResource inictializeResource;

	public static void main(String[] args) {
		SpringApplication.run(ControleDoacoesPrincipalApplication.class, args);
	}

	@PostConstruct
	public void initializeResourse(){
		inictializeResource.createDefaultIntituicao();
	}


}
