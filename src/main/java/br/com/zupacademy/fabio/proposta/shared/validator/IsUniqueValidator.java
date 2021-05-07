package br.com.zupacademy.fabio.proposta.shared.validator;

import br.com.zupacademy.fabio.proposta.shared.config.error.ApiErrorException;
import org.springframework.http.HttpStatus;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class IsUniqueValidator implements ConstraintValidator<IsUnique, String> {

    @PersistenceContext
    private EntityManager entityManager;
    private String columnAttribute;
    private Class<?> entityClass;

    @Override
    public void initialize(IsUnique constraintAnnotation) {
        entityClass = constraintAnnotation.domainClass();
        columnAttribute = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        List<?> resultList = entityManager.createQuery(
                "SELECT 1 FROM "+entityClass.getName()+" WHERE "+columnAttribute+" =: value")
                .setParameter("value", value)
                .getResultList();
        if (resultList.isEmpty()){
            return true;
        }
        throw new ApiErrorException(
                HttpStatus.UNPROCESSABLE_ENTITY,
                String.format("Field %s, %s must be unique", columnAttribute, value)
        );
    }
}
