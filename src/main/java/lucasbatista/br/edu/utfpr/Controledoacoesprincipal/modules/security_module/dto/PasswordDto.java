package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.dto;

import lombok.*;

import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PasswordDto {

    @NotBlank(message = "É obrigatório informar a senha")
    private String password;

    @NotBlank(message = "É obrigatório informar a confirmação da senha")
    private String confirmPassword;

}
