package com.example.demo.api.v1.farmes.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import com.example.demo.api.v1.farmes.model.entity.FarmerEntity;
import com.example.demo.api.v1.farmes.repository.FarmerRepository;
import com.example.demo.common.exceptions.BadRequestException;
import com.example.demo.common.exceptions.NotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DeleteFarmerByIdTest {

    private final FarmerRepository farmers = mock(FarmerRepository.class);
    private final DeleteFarmerById service = new DeleteFarmerById(farmers);

    @Test
    @DisplayName("Когда передаём валидный id, фермер будет удален")
    void execute1() {

        long farmerId = 1L;

        given(farmers.findById(1L)).willReturn(Optional.of(new FarmerEntity()));

        service.execute(farmerId);

        verify(farmers, times(1)).deleteById(farmerId);
    }

    @ParameterizedTest
    @ValueSource(longs = {-1, -2, -3})
    @DisplayName("Если id фермера меньше 1 будет выброшено исключение")
    void execute2(long id) {

        assertThrows(BadRequestException.class, () -> service.execute(id));
    }

    @Test
    @DisplayName("Если id фермера не найдено в базе, будет выброшено исключение")
    void execute3() {
        assertThrows(NotFoundException.class, () -> service.execute(3L));
    }

}