package com.example.demo.api.v1.farmes.service;

import com.example.demo.api.v1.farmes.model.entity.FarmerEntity;
import com.example.demo.api.v1.farmes.model.request.AddFarmerRequest;
import com.example.demo.api.v1.farmes.repository.FarmerRepository;
import com.example.demo.common.exceptions.LightWeightExceptions;
import com.example.demo.validation.service.ValidateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.api.v1.farmes.model.entity.FarmerEntity;
import com.example.demo.api.v1.farmes.model.request.AddFarmerRequest;
import com.example.demo.api.v1.farmes.repository.FarmerRepository;
import com.example.demo.common.exceptions.LightWeightExceptions;
import com.example.demo.validation.service.ValidateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AddFarmerTest {

    private final FarmerRepository farmers = mock(FarmerRepository.class);
    private final ValidateRequest validate = mock(ValidateRequest.class);
    private final AddFarmer service = new AddFarmer(farmers, validate);

    @Test
    @DisplayName("При получении валидного запроса фермер добавится успешно")
    void execute1() {
        final var request = new AddFarmerRequest();
        final var expectedCow = request.toEntity();

        doNothing().when(validate).single(any());
        when(farmers.save(any())).thenReturn(new FarmerEntity());

        service.execute(request);

        verify(validate, times(1)).single(request);
        verify(farmers, times(1)).save(expectedCow);
    }

    @Test
    @DisplayName("При получении невалидного запроса фермер не будет добавлен")
    void execute2() {
        var request = new AddFarmerRequest();

        doThrow(new LightWeightExceptions("123")).when(validate).single(any());

        var ex = assertThrows(
                LightWeightExceptions.class,
                () -> service.execute(request)
        );

        assertEquals("123", ex.getMessage());
        verify(validate, times(1)).single(request);
        verify(farmers, times(0)).save(any());
    }
}