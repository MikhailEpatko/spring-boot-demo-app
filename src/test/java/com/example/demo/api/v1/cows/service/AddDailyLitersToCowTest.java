package com.example.demo.api.v1.cows.service;

import static com.example.demo.api.v1.cows.model.enums.CowStatus.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.api.v1.cows.model.entity.CowEntity;
import com.example.demo.api.v1.cows.model.enums.CowStatus;
import com.example.demo.api.v1.cows.model.request.AddDailyLitersRequest;
import com.example.demo.api.v1.cows.repository.CowRepository;
import com.example.demo.common.exceptions.BadRequestException;
import com.example.demo.common.exceptions.LightWeightExceptions;
import com.example.demo.common.exceptions.NotFoundException;
import com.example.demo.validation.service.ValidateRequest;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AddDailyLitersToCowTest {

    private final CowRepository cows = mock(CowRepository.class);
    private final ValidateRequest validate = mock(ValidateRequest.class);
    private final CowEntity existentCow = mock(CowEntity.class);
    private final AddDailyLitersToCow service = new AddDailyLitersToCow(cows, validate);

    @Test
    @DisplayName("При получении валидного запроса удой коровы зафиксируется успешно")
    void execute1() {
        final var request = new AddDailyLitersRequest();

        doNothing().when(validate).single(any());

        when(cows.findById(request.getId())).thenReturn(Optional.of(existentCow));
        when(existentCow.getStatus()).thenReturn(OK);
        when(existentCow.getLiters()).thenReturn(request.getLiters());

        service.execute(request);

        verify(validate, times(1)).single(request);
        verify(cows, times(2)).findById(request.getId());
        assertEquals(existentCow.getLiters(), request.getLiters());
    }

    @Test
    @DisplayName("При получении невалидного запроса, будет выброшено исключение")
    void execute2() {

        var request = new AddDailyLitersRequest();

        doThrow(new LightWeightExceptions("")).when(validate).single(any());

        assertThrows(LightWeightExceptions.class,
                () -> service.execute(request)
        );

        verify(validate, times(1)).single(request);
        verify(cows, times(0)).findById(request.getId());
        verify(cows, times(0)).save(any());
    }

    @Test
    @DisplayName("При попытке добавить удой корове, id которой нет в базе, будет выброшено исключение")
    void execute3() {

        var request = new AddDailyLitersRequest();

        when(cows.findById(request.getId())).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class,
                () -> service.execute(request)
        );

        verify(validate, times(1)).single(request);
        verify(cows, times(1)).findById(request.getId());
        verify(cows, times(0)).save(any());
    }

    @ParameterizedTest
    @ValueSource(strings = {"SOLD", "DEAD"})
    @DisplayName("При попытке добавить удой корове, статус которой DEAD(Умерла) или SOLD(Продана),"
            + "должно быть выброшено исключение")
    void execute4(CowStatus status) {

        var request = new AddDailyLitersRequest();

        doNothing().when(validate).single(request);

        when(cows.findById(request.getId())).thenReturn(Optional.of(existentCow));
        when(existentCow.getStatus()).thenReturn(status);

        assertThrows(BadRequestException.class,
                () -> service.execute(request));

        verify(validate, times(1)).single(request);
        verify(cows, times(2)).findById(request.getId());
        verify(cows, times(0)).save(any());
    }
}