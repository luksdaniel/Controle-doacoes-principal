package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginCredentials {

    private String username;
    private String password;

}
