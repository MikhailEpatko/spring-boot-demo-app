package com.example.demo.api.v1.farmes.model.request;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.example.demo.validation.service.ValidateRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AddFarmerRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final ValidateRequest validate = new ValidateRequest(validator);

    @Test
    @DisplayName("При проверке валидного запроса не будет выброшено исключение")
    void validateRequest1() {
        var request = new AddFarmerRequest(1L, "FirstName", "MiddleName", "LastName");

        assertDoesNotThrow(() -> validate.single(request));
    }

    @Test
    @DisplayName("Если id фермера меньше 1, должно быть выброшено исключение")
    void validateRequest2() {
        var request = new AddFarmerRequest(0L, "FirstName", "MiddleName", "LastName");
        var expectedError = "id: Не может быть меньше 1";

        var ex = assertThrows(
                ConstraintViolationException.class,
                () -> validate.single(request)
        );
        assertNotNull(ex);
        assertEquals(expectedError, ex.getMessage());
    }

    @Test
    @DisplayName("Если id фермера null, должно быть выброшено исключение")
    void validateRequest3() {
        var request = new AddFarmerRequest
                (null, "FirstName", "MiddleName", "LastName");
        var expectedError = "id: Не может быть null";

        var ex = assertThrows(
                ConstraintViolationException.class,
                () -> validate.single(request)
        );
        assertNotNull(ex);
        assertEquals(expectedError, ex.getMessage());
    }

    @Test
    @DisplayName("Если firstName фермера null, должно быть выброшено исключение")
    void validateRequest4() {
        var request = new AddFarmerRequest(1L, null, "MiddleName", "LastName");
        var expectedError = "firstName: Не может быть null или состоять из пробельных символов";

        var ex = assertThrows(
                ConstraintViolationException.class,
                () -> validate.single(request)
        );
        assertNotNull(ex);
        assertEquals(expectedError, ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  ", "   ", "\n", "\t"})
    @DisplayName("Если firstName состоит из пробельных символов, должно быть выброшено исключение")
    void validateRequest5(String firstName) {
        var request = new AddFarmerRequest(1L, firstName, "MiddleName", "LastName");
        var expectedError = "firstName: Не может быть null или состоять из пробельных символов";

        var ex = assertThrows(
                ConstraintViolationException.class,
                () -> validate.single(request)
        );
        assertNotNull(ex);
        assertEquals(expectedError, ex.getMessage());
    }

    @Test
    @DisplayName("Если middleName фермера null, должно быть выброшено исключение")
    void validateRequest6() {
        var request = new AddFarmerRequest(1L, "firstName", null, "LastName");
        var expectedError = "middleName: Не может быть null или состоять из пробельных символов";

        var ex = assertThrows(
                ConstraintViolationException.class,
                () -> validate.single(request)
        );
        assertNotNull(ex);
        assertEquals(expectedError, ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  ", "   ", "\n", "\t"})
    @DisplayName("Если middleName состоит из пробельных символов, должно быть выброшено исключение")
    void validateRequest7(String middleName) {
        var request = new AddFarmerRequest(1L, "firstName", middleName, "LastName");
        var expectedError = "middleName: Не может быть null или состоять из пробельных символов";

        var ex = assertThrows(
                ConstraintViolationException.class,
                () -> validate.single(request)
        );
        assertNotNull(ex);
        assertEquals(expectedError, ex.getMessage());
    }

    @Test
    @DisplayName("Если lastName фермера null, должно быть выброшено исключение")
    void validateRequest8() {
        var request = new AddFarmerRequest(1L, "firstName", "middleName", null);
        var expectedError = "lastName: Не может быть null или состоять из пробельных символов";

        var ex = assertThrows(
                ConstraintViolationException.class,
                () -> validate.single(request)
        );
        assertNotNull(ex);
        assertEquals(expectedError, ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  ", "   ", "\n", "\t"})
    @DisplayName("Если lastName состоит из пробельных символов, должно быть выброшено исключение")
    void validateRequest9(String lastName) {
        var request = new AddFarmerRequest(1L, "firstName", "middleName", lastName);
        var expectedError = "lastName: Не может быть null или состоять из пробельных символов";

        var ex = assertThrows(
                ConstraintViolationException.class,
                () -> validate.single(request)
        );
        assertNotNull(ex);
        assertEquals(expectedError, ex.getMessage());
    }
}