package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorEntitty {
    private String campo;
    private Object valorRejeitado;
    private String mensagemErro;
}

