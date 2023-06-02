package br.com.hungry.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class RestAlreadyExistsException extends RuntimeException {

    public RestAlreadyExistsException(String message) {
        super(message);
    }

}