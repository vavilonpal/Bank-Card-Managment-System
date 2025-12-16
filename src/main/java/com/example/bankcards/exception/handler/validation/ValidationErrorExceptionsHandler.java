package com.example.bankcards.exception.handler.validation;

import com.example.bankcards.dto.validation.response.ValidationErrorResponse;
import com.example.bankcards.util.validation.Violation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@ControllerAdvice
public class ValidationErrorExceptionsHandler {


    /**
     * Метод для обработки ошибок валидации внутри тела запроса
     *
     * @param e - ConstraintViolationException - метод ловит это исключение и обрабаывает его получая из его тела все ошибки
     * @return ErrorResponse
     */
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onConstraintValidationException(ConstraintViolationException e) {
        final List<Violation> violations = e.getConstraintViolations().stream()
                .map(
                        violation -> new Violation(
                                violation.getPropertyPath().toString(),
                                violation.getMessage()
                        )
                )
                .collect(Collectors.toList());
        return new ValidationErrorResponse(violations);
    }


    /**
     * Метод обрабатывает ошибки валидации, которые применяются к самому объекту,
     *  то есть сама аннотация стоит над классом.
     * @param exception MethodArgumentNotValidException
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onMethodArgumentNotValidException(
            MethodArgumentNotValidException exception
    ) {
        final List<Violation> violations = Stream.concat(
                exception.getBindingResult().getFieldErrors().stream()
                        .map(error -> new Violation(error.getField(), error.getDefaultMessage())),
                exception.getBindingResult().getGlobalErrors().stream()
                        .map(error -> new Violation(error.getObjectName(), error.getDefaultMessage()))
        ).toList();
        return new ValidationErrorResponse(violations);
    }
}
