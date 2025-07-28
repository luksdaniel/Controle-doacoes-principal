package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.service.recoveryToken;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.dto.RecoveryCredentialsDto;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.entity.RecoveryToken;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.entity.Usuario;

public interface RecoveryTokenService {

    void createToken(String email);
    RecoveryToken findToken(String recoveryToken);
    void createRecoveryToken(Usuario user, String token);
    void recoverPassword(RecoveryCredentialsDto recoveryCredentials);

    RecoveryToken validateRecoveryToken(RecoveryCredentialsDto recoveryCredentialsDto);

}
