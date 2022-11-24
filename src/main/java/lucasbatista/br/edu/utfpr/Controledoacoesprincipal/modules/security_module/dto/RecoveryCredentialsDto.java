package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RecoveryCredentialsDto {

    String token;
    String password;

}