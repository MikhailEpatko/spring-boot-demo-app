package com.example.demo.api.v1.cows.service;

import com.example.demo.api.v1.cows.model.entity.CowEntity;
import com.example.demo.api.v1.cows.repository.CowRepository;
import com.example.demo.validation.service.ValidateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetAllCowsTest {

    private final CowRepository cows = mock(CowRepository.class);
    private final ValidateRequest valid = mock(ValidateRequest.class);

    private final GetAllCows service = new GetAllCows(cows, valid);

    @Test
    @DisplayName("при запросе получаем список всех коров")
    void execute() {

        var cow1 = new CowEntity();
        var cow2 = new CowEntity();
        var cow3 = new CowEntity();
        var expected = List.of(cow1, cow2, cow3);
        when(cows.findAll()).thenReturn(expected);

        var result = service.execute();

        assertEquals(result, expected);
    }
}