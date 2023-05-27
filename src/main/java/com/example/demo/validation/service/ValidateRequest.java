package com.example.demo.validation.service;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateRequest {

    private final Validator validator;

    public <T> void single(T request) {
        var violations = validator.validate(request);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    public <T> void collection(Collection<T> request) {
        var violations = request.stream()
                .flatMap(it -> validator.validate(it).stream())
                .collect(Collectors.toSet());
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }
}
