package io.teamchallenge.woodCrafts.controllers.advice;

import io.teamchallenge.woodCrafts.exception.DuplicateException;
import io.teamchallenge.woodCrafts.exception.EntityNotFoundException;
import io.teamchallenge.woodCrafts.exception.ErrorInformation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorInformation> handleEntityNotFoundException(Exception ex) {
        log.error("Entity not found exception: ", ex);

        return getResponseBody(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorInformation> handleConstraintViolationException(Exception ex) {
        log.error("ConstraintViolationException:", ex);

        return getResponseBody(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ErrorInformation> handleDuplicateException(Exception ex) {
        return getResponseBody(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorInformation> handleRequestNotValidException(MethodArgumentNotValidException e) {
        List<String> errors = new ArrayList<>();
        e.getBindingResult()
                .getFieldErrors().forEach(fieldError -> errors.add(fieldError.getObjectName() + ":" + fieldError.getDefaultMessage()));
        String message = String.format("Validation of request failing '%s", (String.join(", ", errors)));
        return getResponseBody(BAD_REQUEST, message);
    }


    private ResponseEntity<ErrorInformation> getResponseBody(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorInformation.of(message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInformation> handleUnknownException(Exception ex) {
        return getResponseBody(INTERNAL_SERVER_ERROR, ex.getMessage());
    }

}

