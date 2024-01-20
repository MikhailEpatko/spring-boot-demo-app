package com.example.demo.api.v1.cows.service;

import com.example.demo.api.v1.cows.repository.CowRepository;
import com.example.demo.common.exceptions.BadRequestException;
import com.example.demo.common.exceptions.NotFoundException;
import com.example.demo.validation.service.ValidateRequest;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DeleteCowByIdTest {

    private final CowRepository cows = mock(CowRepository.class);
    private final ValidateRequest valid = mock(ValidateRequest.class);

    private final DeleteCowById service = new DeleteCowById(cows, valid);

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    @DisplayName("когда передаём правильный id, удаление происходит успешно")
    void whenDeleteThenOk(long id) {

        when(cows.deleteCowById(id)).thenReturn(1);
        assertDoesNotThrow(() -> service.execute(id));
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    @DisplayName("когда при удалении передаём id меньше 1, получаем ошибку")
    void whenTryDeleteByNotValidIdThenReturnThrow(long id) {
        when(cows.deleteCowById(0)).thenThrow(BadRequestException.class);
        assertThrows(BadRequestException.class, () -> service.execute(0));
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    @DisplayName("когда хотим удалить корову id которой не найдено в базе, получаем ошибку")
    void whenCowNotInBaseThenReturnThrow(long id) {
        when(cows.deleteCowById(4)).thenThrow(NotFoundException.class);
        assertThrows(NotFoundException.class, () -> service.execute(4));
    }
}