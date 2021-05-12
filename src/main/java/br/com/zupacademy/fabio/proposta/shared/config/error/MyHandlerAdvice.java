package br.com.zupacademy.fabio.proposta.shared.config.error;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestControllerAdvice
public class MyHandlerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseError> handle(MethodArgumentNotValidException methodArgumentNotValidException) {
        Collection<String> messages = new ArrayList<>();
        BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        fieldErrors.forEach(fieldError -> {
            String message = String.format("Field %s, %s", fieldError.getField(), fieldError.getDefaultMessage());
            messages.add(message);
        });

        ResponseError responseError = new ResponseError(messages);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseError> handle(IllegalArgumentException illegalArgumentException) {
        Collection<String> messages = new ArrayList<>();
        String message = illegalArgumentException.getLocalizedMessage();
        message = String.format("Error on: %s", message);
        messages.add(message);
        ResponseError responseError = new ResponseError(messages);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
    }

    @ExceptionHandler(ApiErrorException.class)
    public ResponseEntity<ResponseError> handleApiErrorException(ApiErrorException apiErrorException) {
        Collection<String> messages = new ArrayList<>();
        messages.add(apiErrorException.getReason());

        ResponseError responseError = new ResponseError(messages);
        return ResponseEntity.status(apiErrorException.getHttpStatus()).body(responseError);
    }
}
