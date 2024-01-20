package com.example.demo.api.v1.cows.service;

import com.example.demo.api.v1.cows.model.entity.CowEntity;
import com.example.demo.api.v1.cows.repository.CowRepository;
import com.example.demo.common.exceptions.BadRequestException;
import com.example.demo.validation.service.ValidateRequest;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class GetAllCowsFarmerByFarmerIdTest {

    private final CowRepository cows = mock(CowRepository.class);
    private final ValidateRequest valid = mock(ValidateRequest.class);

    private final GetAllCowsFarmerByFarmerId service = new GetAllCowsFarmerByFarmerId(cows, valid);

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    @DisplayName("когда передаём правильный id, не должно быть выброшено исключение")
    void execute1(long id) {
        var cow1 = new CowEntity();
        var cow2 = new CowEntity();
        var cow3 = new CowEntity();
        var expected = List.of(cow1, cow2, cow3);

        when(cows.findAllByFarmerId(id)).thenReturn(expected);

        var result = service.execute(id);

        assertEquals(expected, result);
    }

    @ParameterizedTest
    @ValueSource(longs = {-1, 0, -3})
    @DisplayName("когда передаём не валидный id, будет выброшено исключение")
    void execute2(long id) {
        assertThrows(BadRequestException.class, () -> service.execute(id));
    }
}