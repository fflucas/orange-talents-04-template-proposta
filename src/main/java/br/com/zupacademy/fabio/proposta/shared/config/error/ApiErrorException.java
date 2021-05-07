package br.com.zupacademy.fabio.proposta.shared.config.error;

import org.springframework.http.HttpStatus;

public class ApiErrorException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String reason;

    public ApiErrorException(HttpStatus httpStatus, String reason) {
        super(reason);
        this.httpStatus = httpStatus;
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}