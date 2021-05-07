package br.com.zupacademy.fabio.proposta;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@ConstraintComposition(CompositionType.OR)
@Constraint(validatedBy = {})
@CPF
@CNPJ
public @interface CpfOrCnpjValidator {
    String message() default "{or.hibernate.validator.constraints.CPF" +
                            "org.hibernate.validator.constraints.br.CNPJ}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
