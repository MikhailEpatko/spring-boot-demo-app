package com.example.demo.api.v1.cows.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.demo.api.v1.cows.model.entity.CowEntity;
import com.example.demo.api.v1.cows.repository.CowRepository;
import com.example.demo.common.exceptions.BadRequestException;
import com.example.demo.validation.service.ValidateRequest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class GetAllCowsFarmerByFarmerIdTest {

    private final CowRepository cows = mock(CowRepository.class);
    private final ValidateRequest valid = mock(ValidateRequest.class);

    private final GetAllCowsFarmerByFarmerId service = new GetAllCowsFarmerByFarmerId(cows, valid);

    @Test
    @DisplayName("когда передаём правильный id, не должно быть выброшено исключение")
    void execute1() {
        var cow1 = new CowEntity();
        cow1.setFarmerId(1L);

        var cow2 = new CowEntity();
        var cow3 = new CowEntity();

        cows.save(cow1);
        cows.save(cow2);
        cows.save(cow3);

        var resultList = List.of(cow1);

        when(cows.findAllByFarmerId(1L)).thenReturn(resultList);
        assertDoesNotThrow(() -> service.execute(1));
    }

    @Test
    @DisplayName("когда передаём не валидный id, будет выброшено исключение")
    void execute() {
        assertThrows(BadRequestException.class, () -> service.execute(0));
    }
}