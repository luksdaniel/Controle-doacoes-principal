package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CpfValidation implements ConstraintValidator<Cpf_NotNull, String> {

    @Override
    public void initialize(Cpf_NotNull constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.isEmpty();
    }
}
