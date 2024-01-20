package com.example.demo.api.v1.cows.model.request;

import com.example.demo.api.v1.cows.model.enums.Color;
import com.example.demo.api.v1.cows.model.enums.CowStatus;
import com.example.demo.validation.service.ValidateRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AddCowRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final ValidateRequest validate = new ValidateRequest(validator);

    @Test
    @DisplayName("При проверке валидного запроса не будет выброшено исключение")
    void validateRequest1() {
        var request = new AddCowRequest(1L, "name", Color.RED, 1, CowStatus.OK);

        assertDoesNotThrow(() -> validate.single(request));
    }

    @Test
    @DisplayName("Если id фермера меньше 1, то должно быть выброшено исключение")
    void validateRequest2() {
        var request = new AddCowRequest(0L, "name", Color.RED, 1, CowStatus.OK);
        var expectedError = "farmerId: Не может быть меньше 1";

        var ex = assertThrows(
            ConstraintViolationException.class,
            () -> validate.single(request)
        );

        assertNotNull(ex);
        assertEquals(expectedError, ex.getMessage());
    }

    @Test
    @DisplayName("Если id фермера null, то должно быть выброшено исключение")
    void validateRequest3() {
        var request = new AddCowRequest(null, "name", Color.RED, 1, CowStatus.OK);
        var expectedError = "farmerId: Не может быть null";

        var ex = assertThrows(
            ConstraintViolationException.class,
            () -> validate.single(request)
        );

        assertNotNull(ex);
        assertEquals(expectedError, ex.getMessage());
    }
}