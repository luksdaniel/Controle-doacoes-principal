package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.entity;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorResponse implements Serializable {

    List<String> errorMessage;

}
