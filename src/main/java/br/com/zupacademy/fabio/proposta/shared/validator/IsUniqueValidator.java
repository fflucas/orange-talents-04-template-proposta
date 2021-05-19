package br.com.zupacademy.fabio.proposta.shared.validator;

import br.com.zupacademy.fabio.proposta.client.Proposta;
import br.com.zupacademy.fabio.proposta.client.RepositoryProposta;
import br.com.zupacademy.fabio.proposta.shared.config.error.ApiErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class IsUniqueValidator implements ConstraintValidator<IsUnique, String> {

    private RepositoryProposta repositoryProposta;
    private String columnAttribute;
    private Class<?> entityClass;

    @Autowired
    public IsUniqueValidator(RepositoryProposta repositoryProposta) {
        this.repositoryProposta = repositoryProposta;
    }

    @Override
    public void initialize(IsUnique constraintAnnotation) {
        entityClass = constraintAnnotation.domainClass();
        columnAttribute = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        Optional<Proposta> byDocument = repositoryProposta.findByDocument(value);
        if(byDocument.isEmpty()){
            return true;
        }

        throw new ApiErrorException(
                HttpStatus.UNPROCESSABLE_ENTITY,
                String.format("Field %s, %s must be unique", columnAttribute, value)
        );
    }
}
