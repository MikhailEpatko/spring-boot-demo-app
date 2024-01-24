package com.example.demo.api.v1.cows.model.request;

import com.example.demo.validation.service.ValidateRequest;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.example.demo.api.v1.cows.model.enums.Color.RED;
import static com.example.demo.api.v1.cows.model.enums.CowStatus.OK;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AddCowRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final ValidateRequest validate = new ValidateRequest(validator);

    @Test
    @DisplayName("При проверке валидного запроса не будет выброшено исключение")
    void validateRequest1() {
        var request = new AddCowRequest(1L, "name", RED, 1, OK);

        assertDoesNotThrow(() -> validate.single(request));
    }

    @Test
    @DisplayName("Если id фермера меньше 1, то должно быть выброшено исключение")
    void validateRequest2() {
        var request = new AddCowRequest(0L, "name", RED, 1, OK);
        var expectedError = "farmerId: Не может быть меньше 1";

        var exception = assertThrows(ConstraintViolationException.class,
                () -> validate.single(request)
        );

        assertNotNull(exception);
        assertEquals(expectedError, exception.getMessage());
    }

    @Test
    @DisplayName("Если id фермера null, то должно быть выброшено исключение")
    void validateRequest3() {
        var request = new AddCowRequest(null, "name", RED, 1, OK);
        var expectedError = "farmerId: Не может быть null";

        var exception = assertThrows(ConstraintViolationException.class,
                () -> validate.single(request)
        );

        assertNotNull(exception);
        assertEquals(expectedError, exception.getMessage());
    }

    @Test
    @DisplayName("Если имя коровы null, должно быть выброшено исключение")
    void validateRequest4() {
        var request = new AddCowRequest(1L, null, RED, 1, OK);
        var expectedError = "name: Не может быть null или состоять из пробельных символов";

        var exception = assertThrows(ConstraintViolationException.class,
                () -> validate.single(request)
        );

        assertNotNull(exception);
        assertEquals(expectedError, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  ", "   ", "\n", "\t"})
    @DisplayName("Если имя коровы состоит из пробельных символов, должно быть выброшено исключение")
    void validateRequest5(String name) {
        var request = new AddCowRequest(1L, name, RED, 1, OK);
        var expectedError = "name: Не может быть null или состоять из пробельных символов";

        var exception = assertThrows(ConstraintViolationException.class,
                () -> validate.single(request)
        );

        assertNotNull(exception);
        assertEquals(expectedError, exception.getMessage());
    }

    @Test
    @DisplayName("Если цвет коровы null, должно быть выброшено исключение")
    void validateRequest6() {
        var request = new AddCowRequest(1L, "Tommy", null, 1, OK);
        var expectedError = "color: Не может быть null";

        var exception = assertThrows(ConstraintViolationException.class,
                () -> validate.single(request)
        );

        assertNotNull(exception);
        assertEquals(expectedError, exception.getMessage());
    }

    @Test
    @DisplayName("Если корова дает меньше 0л молока, должно быть выброшено исключение")
    void validateRequest7() {
        var request = new AddCowRequest(1L, "Tommy", RED, -1, OK);
        var expectedError = "liters: Не может быть меньше 0";

        var exception = assertThrows(ConstraintViolationException.class,
                () -> validate.single(request)
        );

        assertNotNull(exception);
        assertEquals(expectedError, exception.getMessage());
    }

    @Test
    @DisplayName("Если статус коровы null, должно быть выброшено исключение")
    void validateRequest8() {
        var request = new AddCowRequest(1L, "Tommy", RED, 1, null);
        var expectedError = "status: Не может быть null";

        var exception = assertThrows(ConstraintViolationException.class,
                () -> validate.single(request)
        );

        assertNotNull(exception);
        assertEquals(expectedError, exception.getMessage());
    }
}