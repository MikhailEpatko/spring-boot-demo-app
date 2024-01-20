package com.example.demo.api.v1.cows.service;

import com.example.demo.api.v1.cows.model.entity.CowEntity;
import com.example.demo.api.v1.cows.model.request.UpdateCowDetailsRequest;
import com.example.demo.api.v1.cows.repository.CowRepository;
import com.example.demo.common.exceptions.NotFoundException;
import com.example.demo.validation.service.ValidateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UpdateCowTest {

    private final CowRepository cows = mock(CowRepository.class);
    private final ValidateRequest valid = mock(ValidateRequest.class);

    private final UpdateCow service = new UpdateCow(cows, valid);

    @Test
    @DisplayName("когда передаём запрос на обноаление коровы, обновление происходит успешно")
    void whenUpdateThenOk() {

        var updatedCow = new CowEntity();
        when(cows.save(updatedCow)).thenReturn(updatedCow);

        var request = new UpdateCowDetailsRequest();
        var updated = cows.findById(request.getId()).orElse(new CowEntity());
        updated.update(request);
        cows.save(updated);
        assertEquals(updated, updatedCow);
    }

    @Test
    @DisplayName("если корова с заданным id не найдена, должно быть выброшено исключение")
    void whenUpdateCowWithBadIdThenThrow() {

        var updatedCow = new CowEntity();
        updatedCow.setId(null);
        when(cows.save(updatedCow)).thenThrow(NotFoundException.class);

        var request = new UpdateCowDetailsRequest();
        request.setId(null);

        assertThrows(NotFoundException.class, () -> service.execute(request));
    }
}