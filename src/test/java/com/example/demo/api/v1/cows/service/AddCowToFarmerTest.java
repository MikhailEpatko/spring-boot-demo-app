package com.example.demo.api.v1.cows.service;

import com.example.demo.api.v1.cows.model.entity.CowEntity;
import com.example.demo.api.v1.cows.model.request.AddCowRequest;
import com.example.demo.api.v1.cows.repository.CowRepository;
import com.example.demo.validation.service.ValidateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AddCowToFarmerTest {

    private final CowRepository cows = mock(CowRepository.class);
    private final ValidateRequest valid = mock(ValidateRequest.class);

    private final AddCowToFarmer service = new AddCowToFarmer(cows, valid);


    @Test
    @DisplayName("При добавлении новой коровы не возникает ошибок")
    void whenAddSomeThenOk() {

        var newCow = new CowEntity();
        when(cows.save(newCow)).thenReturn(newCow);

        var request = new AddCowRequest();
        cows.save(request.toEntity());
        assertEquals(newCow, request.toEntity());
        assertDoesNotThrow(() -> service.execute(request));
    }
}