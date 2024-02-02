package com.example.demo.api.v1.cows.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.demo.api.v1.cows.model.entity.CowEntity;
import com.example.demo.api.v1.cows.repository.CowRepository;
import com.example.demo.common.exceptions.BadRequestException;
import com.example.demo.common.exceptions.NotFoundException;
import com.example.demo.validation.service.ValidateRequest;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class GetCowByIdTest {

    private final CowRepository cows = mock(CowRepository.class);
    private final ValidateRequest valid = mock(ValidateRequest.class);

    private final GetCowById service = new GetCowById(cows, valid);

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    @DisplayName("когда передаём валидный id, не должно быть выброшено исключение")
    void execute1(long id) {

        var expected = Optional.of(new CowEntity());
        when(cows.findById(id)).thenReturn(expected);

        var result = Optional.of(service.execute(id));

        assertEquals(expected, result);
        assertDoesNotThrow(() -> service.execute(id));
    }

    @ParameterizedTest
    @ValueSource(longs = {0, -1})
    @DisplayName("Если id меньше 1, будет выброшено исключение")
    void execute2(long id) {

        assertThrows(BadRequestException.class, () -> service.execute(id));
    }

    @Test
    @DisplayName("когда передаём id которого нет в базе, должно быть выброшено исключение")
    void execute3() {
        assertThrows(NotFoundException.class, () -> service.execute(1L));
    }
}