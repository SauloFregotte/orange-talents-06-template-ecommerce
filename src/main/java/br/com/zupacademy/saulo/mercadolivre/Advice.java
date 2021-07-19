package br.com.zupacademy.saulo.mercadolivre;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityExistsException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class Advice {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespostaErro invalidEntityData(MethodArgumentNotValidException e){
        return new RespostaErro(e.getBindingResult()
                .getAllErrors()
                .stream()
                .findFirst()
                .map(ObjectError::getDefaultMessage)
                .orElse(e.getMessage()), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespostaErro invalidFormat(ConstraintViolationException e){
        return new RespostaErro(
                e.getConstraintViolations()
                .stream()
                .findFirst()
                .map(ConstraintViolation::getMessageTemplate)
                .orElse(e.getMessage()), HttpStatus.BAD_REQUEST.value()
        );
    }

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespostaErro invalidData(IllegalArgumentException e){
        return new RespostaErro(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

//    @ExceptionHandler({EntityException.class})
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public RespostaErro invalidEntityField(EntityException e){
//        return new RespostaErro(e.getMessage(), HttpStatus.BAD_REQUEST.value());
//    }

    @ExceptionHandler({EntityExistsException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespostaErro entityAlreadyExists(EntityExistsException e){
        return new RespostaErro(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

}
