package com.example.demo.api.v1.cows.model.request;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.demo.validation.service.ValidateRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AddDailyLitersRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final ValidateRequest validate = new ValidateRequest(validator);

    @Test
    @DisplayName("При проверке валидного запроса не будет выброшено исключение")
    void validateAddDailyLitersRequest1() {
        var request = new AddDailyLitersRequest();
        request.setId(1L);
        request.setDate(LocalDateTime.now());
        request.setLiters(10);

        Assertions.assertTrue(validator.validate(request).isEmpty());
        assertDoesNotThrow(() -> validate.single(request));
    }

    @Test
    @DisplayName("Если id коровы меньше 1, должно быть выброшено исключение")
    void validateAddDailyLitersRequest2() {
        var request = new AddDailyLitersRequest();
        request.setId(0L);
        request.setDate(LocalDateTime.now());
        request.setLiters(10);
        var expectedError = "id: Не может быть меньше 1";

        var ex = assertThrows(
                ConstraintViolationException.class,
                () -> validate.single(request)
        );

        assertNotNull(ex);
        assertEquals(expectedError, ex.getMessage());
    }

    @Test
    @DisplayName("Если id коровы null, должно быть выброшено исключение")
    void validateAddDailyLitersRequest3() {
        var request = new AddDailyLitersRequest();
        request.setId(null);
        request.setDate(LocalDateTime.now());
        request.setLiters(10);
        var expectedError = "id: Не может быть null";

        var ex = assertThrows(
                ConstraintViolationException.class,
                () -> validate.single(request)
        );

        assertNotNull(ex);
        assertEquals(expectedError, ex.getMessage());
    }

    @Test
    @DisplayName("Если время обновления  date равно null, должно быть выброшено исключение")
    void validateAddDailyLitersRequest4() {
        var request = new AddDailyLitersRequest();
        request.setId(1L);
        request.setDate(null);
        request.setLiters(10);
        var expectedError = "date: Дата не может быть null";

        var ex = assertThrows(
                ConstraintViolationException.class,
                () -> validate.single(request)
        );

        assertNotNull(ex);
        assertEquals(expectedError, ex.getMessage());
    }

    @Test
    @DisplayName("Если удой коровы меньше 0л, должно быть выброшено исключение")
    void validateUpdateRequest5() {
        var request = new AddDailyLitersRequest();
        request.setId(1L);
        request.setDate(LocalDateTime.now());
        request.setLiters(-1);

        var ex = assertThrows(
                ConstraintViolationException.class,
                () -> validate.single(request)
        );

        var expectedError = "liters: Не может быть меньше 0";

        assertNotNull(ex);
        assertEquals(expectedError, ex.getMessage());
    }

    @Test
    @DisplayName("Если удой коровы больше 20л, должно быть выброшено исключение")
    void validateUpdateRequest6() {
        var request = new AddDailyLitersRequest();
        request.setId(1L);
        request.setDate(LocalDateTime.now());
        request.setLiters(21);

        var ex = assertThrows(
                ConstraintViolationException.class,
                () -> validate.single(request)
        );

        var expectedError = "liters: Не может быть больше 20";

        assertNotNull(ex);
        assertEquals(expectedError, ex.getMessage());
    }
}