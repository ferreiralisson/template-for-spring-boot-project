package com.example.demo.handler;

import com.example.demo.exception.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
@Log4j2
public class RestExceptionHandler  extends ResponseEntityExceptionHandler {

    private static final String MESSAGE_TITLE = "Bad Request, Por favor entrar em contato com o administrador";

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(BadRequestException bre){
        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder()
                        .title(MESSAGE_TITLE)
                        .details(bre.getMessage())
                        .status(BAD_REQUEST.value())
                        .developerMessage(bre.getClass().getName())
                        .timestamp(now())
                        .build(), BAD_REQUEST
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<DataIntegrityViolationExceptionDetails> handlerDataIntegrityViolationException(
            DataIntegrityViolationException dataIntegrityViolationException){
        return new ResponseEntity<>(
                DataIntegrityViolationExceptionDetails.builder()
                        .title(MESSAGE_TITLE)
                        .details(dataIntegrityViolationException.getCause().getCause().toString())
                        .status(BAD_REQUEST.value())
                        .developerMessage(dataIntegrityViolationException.getClass().getName())
                        .timestamp(now())
                        .build(), BAD_REQUEST
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException methodArgumentNotValidException, HttpHeaders headers, HttpStatus status,
            WebRequest request){
        List<FieldError> fieldErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors();
        return new ResponseEntity<>(
                ValidationExceptionDetails.builder()
                        .timestamp(now())
                        .status(BAD_REQUEST.value())
                        .title("Bad Request Exception, Campos Inválidos")
                        .details("Verifique o erro do(s) campo(s)")
                        .developerMessage(methodArgumentNotValidException.getClass().getName())
                        .fields(fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", ")))
                        .fieldsMessage(fieldErrors.stream().map(FieldError::getDefaultMessage)
                                .collect(Collectors.joining(", ")))
                        .build(), BAD_REQUEST
        );
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .timestamp(now())
                .status(status.value())
                .title(ex.getCause().getMessage())
                .details(ex.getMessage())
                .developerMessage(ex.getClass().getName())
                .build();

        return new ResponseEntity<>(exceptionDetails, headers, status);
    }
}
