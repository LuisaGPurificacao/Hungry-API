package br.com.hungry.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class RestNotFoundException extends RuntimeException {

    public RestNotFoundException(String message) {
        super(message);
    }

}