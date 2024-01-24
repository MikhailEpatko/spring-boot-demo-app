package com.example.demo.api.v1.cows.service;

import com.example.demo.api.v1.cows.model.entity.CowEntity;
import com.example.demo.api.v1.cows.model.request.UpdateCowDetailsRequest;
import com.example.demo.api.v1.cows.repository.CowRepository;
import com.example.demo.common.exceptions.LightWeightExceptions;
import com.example.demo.common.exceptions.NotFoundException;
import com.example.demo.validation.service.ValidateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doThrow;

class UpdateCowTest {

    private final CowRepository cows = mock(CowRepository.class);
    private final ValidateRequest valid = mock(ValidateRequest.class);

    private final UpdateCow service = new UpdateCow(cows, valid);

    @Test
    @DisplayName("При получении валидного запроса, обновление происходит успешно")
    void execute1() {

        var request = new UpdateCowDetailsRequest();


        doNothing().when(valid).single(any());

        when(cows.findById(any())).thenReturn(Optional.of(new  CowEntity()));
        when(cows.save(any())).thenReturn(new CowEntity());

        service.execute(request);

        verify(valid, times(1)).single(request);
        verify(cows, times(1)).findById(request.getId());
        verify(cows, times(1)).save(any());
    }

    @Test
    @DisplayName("При получении невалидного запроса, будет выброшено исключение")
    void execute2() {

        var request = new UpdateCowDetailsRequest();

        doThrow(new LightWeightExceptions("")).when(valid).single(any());

        assertThrows(LightWeightExceptions.class,
                () -> service.execute(request)
        );

        verify(valid, times(1)).single(request);
        verify(cows, times(0)).findById(request.getId());
        verify(cows, times(0)).save(any());
    }

    @Test
    @DisplayName("При попытке обновить данные коровы, id которой нет в базе, будет выброшено исключение")
    void execute3() {

        var request = new UpdateCowDetailsRequest();

        when(cows.findById(request.getId())).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class,
                () -> service.execute(request)
        );

        verify(valid, times(1)).single(request);
        verify(cows, times(1)).findById(request.getId());
        verify(cows, times(0)).save(any());
    }
}