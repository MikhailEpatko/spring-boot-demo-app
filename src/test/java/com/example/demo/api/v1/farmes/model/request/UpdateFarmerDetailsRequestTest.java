package com.example.demo.api.v1.farmes.model.request;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.example.demo.validation.service.ValidateRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class UpdateFarmerDetailsRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final ValidateRequest validate = new ValidateRequest(validator);

    @Test
    @DisplayName("При проверке валидного запроса не будет выброшено исключение")
    void validateUpdateRequest1() {
        var request = new UpdateFarmerDetailsRequest
                (1L, "FirstName", "MiddleName", "LastName");

        assertDoesNotThrow(() -> validate.single(request));
    }

    @Test
    @DisplayName("Если id фермера null, должно быть выброшено исключение")
    void validateUpdateRequest2() {
        var request = new UpdateFarmerDetailsRequest
                (null, "firstName", "MiddleName", "LastName");
        var expectedError = "id: Не может быть null";

        var exception = assertThrows(ConstraintViolationException.class,
                () -> validate.single(request)
        );
        assertNotNull(exception);
        assertEquals(expectedError, exception.getMessage());
    }

    @Test
    @DisplayName("Если id фермера меньше единицы, должно быть выброшено исключение")
    void validateUpdateRequest3() {
        var request = new UpdateFarmerDetailsRequest
                (0L, "firstName", "MiddleName", "LastName");
        var expectedError = "id: Не может быть меньше 1";

        var exception = assertThrows(ConstraintViolationException.class,
                () -> validate.single(request)
        );
        assertNotNull(exception);
        assertEquals(expectedError, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  ", "   ", "\n", "\t"})
    @DisplayName("Если firstName фермера пустое или состоит из пробельных символов, должно быть выброшено исключение")
    void validateUpdateRequest4(String firstName) {
        var request = new UpdateFarmerDetailsRequest(1L, firstName, "middleName", "lastName");
        var expectedError = "firstName: Не может быть пустым или состоять из пробельных символов";

        var exception = assertThrows(ConstraintViolationException.class,
                () -> validate.single(request)
        );

        assertNotNull(exception);
        assertEquals(expectedError, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  ", "   ", "\n", "\t"})
    @DisplayName("Если middleName фермера пустое или состоит из пробельных символов, должно быть выброшено исключение")
    void validateUpdateRequest5(String middleName) {
        var request = new UpdateFarmerDetailsRequest(1L, "firstName", middleName, "lastName");
        var expectedError = "middleName: Не может быть пустым или состоять из пробельных символов";

        var exception = assertThrows(ConstraintViolationException.class,
                () -> validate.single(request)
        );

        assertNotNull(exception);
        assertEquals(expectedError, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  ", "   ", "\n", "\t"})
    @DisplayName("Если lastName фермера пустое или состоит из пробельных символов, должно быть выброшено исключение")
    void validateUpdateRequest6(String lastName) {
        var request = new UpdateFarmerDetailsRequest(1L, "firstName", "middleName", lastName);
        var expectedError = "lastName: Не может быть пустым или состоять из пробельных символов";

        var exception = assertThrows(ConstraintViolationException.class,
                () -> validate.single(request)
        );

        assertNotNull(exception);
        assertEquals(expectedError, exception.getMessage());
    }
}