package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CpfValidation.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Cpf_NotNull {

    String message() default "CPF inválido";
    Class <?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
