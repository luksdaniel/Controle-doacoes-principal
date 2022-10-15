package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CpfValidation implements ConstraintValidator<Cpf_NotNull, String> {

    @Override
    public void initialize(Cpf_NotNull constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s.isBlank())
            return true;



        return false;
    }
}
