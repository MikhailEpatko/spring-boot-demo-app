package com.example.demo.api.v1.cows.service;

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
import com.example.demo.api.v1.cows.model.request.AddCowRequest;
import com.example.demo.api.v1.cows.repository.CowRepository;
import com.example.demo.common.exceptions.LightWeightExceptions;
import com.example.demo.validation.service.ValidateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AddCowToFarmerTest {

    private final CowRepository cows = mock(CowRepository.class);
    private final ValidateRequest validate = mock(ValidateRequest.class);
    private final AddCowToFarmer service = new AddCowToFarmer(cows, validate);


    @Test
    @DisplayName("При получении валидного запроса корова добавится успешно")
    void execute1() {
        final var request = new AddCowRequest();
        final var expectedCow = request.toEntity();

        doNothing().when(validate).single(any());
        when(cows.save(any())).thenReturn(new CowEntity());

        service.execute(request);

        verify(validate, times(1)).single(request);
        verify(cows, times(1)).save(expectedCow);
    }

    @Test
    @DisplayName("При получении невалидного запроса корова не будет добавлена")
    void execute2() {
        var request = new AddCowRequest();

        doThrow(new LightWeightExceptions("123")).when(validate).single(any());

        var ex = assertThrows(
                LightWeightExceptions.class,
                () -> service.execute(request)
        );

        assertEquals("123", ex.getMessage());
        verify(validate, times(1)).single(request);
        verify(cows, times(0)).save(any());
    }
}