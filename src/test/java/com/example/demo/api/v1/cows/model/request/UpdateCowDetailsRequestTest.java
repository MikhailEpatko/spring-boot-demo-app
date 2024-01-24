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

class UpdateCowDetailsRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final ValidateRequest validate = new ValidateRequest(validator);

    @Test
    @DisplayName("При проверке валидного запроса не будет выброшено исключение")
    void validateUpdateRequest1() {
        var request = new UpdateCowDetailsRequest(1L, 1L, "name", RED, 1, OK);

        assertDoesNotThrow(() -> validate.single(request));
    }

    @Test
    @DisplayName("Если id коровы null должно быть выброшено исключение")
    void validateUpdateRequest2() {
        var request = new UpdateCowDetailsRequest(null, 1L, "name", RED, 1, OK);
        var expectedError = "id: Не может быть null";

        var exception = assertThrows(ConstraintViolationException.class,
                () -> validate.single(request)
        );
        assertNotNull(exception);
        assertEquals(expectedError, exception.getMessage());
    }

    @Test
    @DisplayName("Если id коровы меньше 1, должно быть выброшено исключение")
    void validateUpdateRequest3() {
        var request = new UpdateCowDetailsRequest(0L, 1L, "name", RED, 1, OK);
        var expectedError = "id: Не может быть меньше 1";

        var exception = assertThrows(ConstraintViolationException.class,
                () -> validate.single(request)
        );
        assertNotNull(exception);
        assertEquals(expectedError, exception.getMessage());
    }

    @Test
    @DisplayName("Если id фермера меньше 1, должно быть выброшено исключение")
    void validateUpdateRequest4() {
        var request = new UpdateCowDetailsRequest(1L, 0L, "name", RED, 1, OK);
        var expectedError = "farmerId: Не может быть меньше 1";

        var exception = assertThrows(ConstraintViolationException.class,
                () -> validate.single(request)
        );
        assertNotNull(exception);
        assertEquals(expectedError, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  ", "   ", "\n", "\t"})
    @DisplayName("Если имя коровы пустое или состоит из пробельных символов, должно быть выброшено исключение")
    void validateUpdateRequest5(String name) {
        var request = new UpdateCowDetailsRequest(1L, 1L, name, RED, 1, OK);
        var expectedError = "name: Не может быть пустым или состоять из пробельных символов";

        var exception = assertThrows(ConstraintViolationException.class,
                () -> validate.single(request)
        );

        assertNotNull(exception);
        assertEquals(expectedError, exception.getMessage());
    }

    @Test
    @DisplayName("Если удой коровы меньше 0л, должно быть выброшено исключение")
    void validateUpdateRequest6() {
        var request = new UpdateCowDetailsRequest(1L, 1L, "name", RED, -1, OK);
        var expectedError = "liters: Не может быть меньше 0";

        var exception = assertThrows(ConstraintViolationException.class,
                () -> validate.single(request)
        );
        assertNotNull(exception);
        assertEquals(expectedError, exception.getMessage());
    }
}