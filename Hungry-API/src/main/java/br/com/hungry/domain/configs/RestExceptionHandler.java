package br.com.hungry.domain.configs;

import br.com.hungry.app.dtos.RestValidationError;
import br.com.hungry.app.dtos.StatusErrorResponse;
import br.com.hungry.domain.exceptions.RestAlreadyExistsException;
import br.com.hungry.domain.exceptions.RestNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<StatusErrorResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("Erro de validação");
        List<RestValidationError> errors = new ArrayList<>();
        e.getFieldErrors().forEach(v -> errors.add(new RestValidationError(v.getField(), v.getDefaultMessage())));
        return ResponseEntity.badRequest().body(new StatusErrorResponse(HttpStatus.BAD_REQUEST.value(), "Erros de validação", errors));
    }

    @ExceptionHandler(RestNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<StatusErrorResponse> restNotFoundExceptionHandler(RestNotFoundException e) {
        log.error("Erro de entidade não encontrada");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StatusErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
    }

    @ExceptionHandler(RestAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<StatusErrorResponse> restAlreadyExistsExceptionHandler(RestAlreadyExistsException e) {
        log.error("Erro de entidade já existe");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new StatusErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage(), null));
    }

}