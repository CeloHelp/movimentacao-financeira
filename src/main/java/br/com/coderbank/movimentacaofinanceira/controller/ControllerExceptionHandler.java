package br.com.coderbank.movimentacaofinanceira.controller;

import br.com.coderbank.movimentacaofinanceira.exceptions.custom.ContaException;
import br.com.coderbank.movimentacaofinanceira.exceptions.dto.ErroResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.HashMap;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(ContaException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResponseDTO handleException(ContaException ex) {
        return new ErroResponseDTO(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErroResponseDTO handleIllegalArgument(IllegalArgumentException ex) {
        return new ErroResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
    }

    private static HashMap<Object, Object> builderHashMapValidationErros(MethodArgumentNotValidException exception) {
        final var errors = new HashMap<>();

        exception.getBindingResult()
                .getAllErrors()
                .forEach(error -> {
                    var fieldName = ((FieldError) error).getField();

                    var errorMessage = error.getDefaultMessage();

                    errors.put(fieldName, errorMessage);
                });
        return errors;
    }

}
