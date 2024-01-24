package com.example.demo.api.v1.cows.service;

import com.example.demo.api.v1.cows.repository.CowRepository;
import com.example.demo.common.exceptions.BadRequestException;
import com.example.demo.common.exceptions.NotFoundException;
import com.example.demo.validation.service.ValidateRequest;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DeleteCowByIdTest {

    private final CowRepository cows = mock(CowRepository.class);
    private final ValidateRequest valid = mock(ValidateRequest.class);

    private final DeleteCowById service = new DeleteCowById(cows, valid);

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    @DisplayName("Когда передаём валидный id, корова будет удалена, метод возвращает 1")
    void execude1(long id) {
        var expected = 1;
        when(cows.deleteCowById(id)).thenReturn(expected);

        var result = service.execute(id);
        assertEquals(expected, result);
        assertTrue(cows.findById(id).isEmpty());
    }

    @ParameterizedTest
    @ValueSource(longs = {-1, -2, -3})
    @DisplayName("Если id коровы меньше 1 будет выброшено исключение")
    void execute2(long id) {

        assertThrows(BadRequestException.class, () -> service.execute(id));
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2})
    @DisplayName("Если id коровы не найдено в базе, будет выброшено исключение")
    void execute3(long id) {

        assertThrows(NotFoundException.class, () -> service.execute(3L));
    }
}