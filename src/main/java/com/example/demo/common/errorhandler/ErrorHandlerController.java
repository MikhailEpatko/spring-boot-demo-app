package com.example.demo.common.errorhandler;

import com.example.demo.common.exceptions.BadRequestException;
import com.example.demo.common.exceptions.NotFoundException;
import com.example.demo.validation.model.ErrorResponse;
import com.example.demo.validation.model.Violation;
import jakarta.validation.ConstraintViolationException;
import static java.util.Collections.singletonList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandlerController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse handleValidationErrors(ConstraintViolationException ex) {
        var errors = ex.getConstraintViolations()
                .stream()
                .map(it -> new Violation(it.getPropertyPath().toString(), it.getMessage()))
                .collect(Collectors.toList());

        return new ErrorResponse(errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ErrorResponse handleJpaObjectRetrievalFailure(BadRequestException ex) {
        return new ErrorResponse(violation((ex)));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse handleNotFoundEntityException(NotFoundException ex) {
        return new ErrorResponse(violation(ex));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleGenericErrors(Exception ex) {
        log.error("Ошибка", ex);
        return new ErrorResponse(violation(ex));
    }

    private List<Violation> violation(Exception ex) {
        return singletonList(new Violation(ex.getClass().getSimpleName(), ex.getMessage()));
    }
}
