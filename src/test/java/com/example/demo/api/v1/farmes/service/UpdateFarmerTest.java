package com.example.demo.api.v1.farmes.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.api.v1.farmes.model.entity.FarmerEntity;
import com.example.demo.api.v1.farmes.model.request.UpdateFarmerDetailsRequest;
import com.example.demo.api.v1.farmes.repository.FarmerRepository;
import com.example.demo.common.exceptions.LightWeightExceptions;
import com.example.demo.common.exceptions.NotFoundException;
import com.example.demo.validation.service.ValidateRequest;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UpdateFarmerTest {

    private final FarmerRepository farmers = mock(FarmerRepository.class);
    private final ValidateRequest validate = mock(ValidateRequest.class);
    private final UpdateFarmer service = new UpdateFarmer(farmers, validate);

    @Test
    @DisplayName("При получении валидного запроса, обновление происходит успешно")
    void execute1() {

        final var request = new UpdateFarmerDetailsRequest();

        doNothing().when(validate).single(any());

        when(farmers.findById(any())).thenReturn(Optional.of(new FarmerEntity()));
        when(farmers.save(any())).thenReturn(new FarmerEntity());

        service.execute(request);

        verify(validate, times(1)).single(request);
        verify(farmers, times(1)).findById(request.getId());
        verify(farmers, times(1)).save(any());
    }

    @Test
    @DisplayName("При получении невалидного запроса, будет выброшено исключение")
    void execute2() {

        var request = new UpdateFarmerDetailsRequest();

        doThrow(new LightWeightExceptions("")).when(validate).single(any());

        assertThrows(LightWeightExceptions.class,
                () -> service.execute(request)
        );

        verify(validate, times(1)).single(request);
        verify(farmers, times(0)).findById(request.getId());
        verify(farmers, times(0)).save(any());
    }

    @Test
    @DisplayName("При попытке обновить данные фермера, id которого нет в базе, будет выброшено исключение")
    void execute3() {

        var request = new UpdateFarmerDetailsRequest();

        when(farmers.findById(request.getId())).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class,
                () -> service.execute(request)
        );

        verify(validate, times(1)).single(request);
        verify(farmers, times(1)).findById(request.getId());
        verify(farmers, times(0)).save(any());
    }
}