package com.example.demo.api.v1.farmes.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.example.demo.api.v1.farmes.model.entity.FarmerEntity;
import com.example.demo.api.v1.farmes.repository.FarmerRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GetAllFarmersTest {

    private final FarmerRepository farmers = mock(FarmerRepository.class);
    private final GetAllFarmers service = new GetAllFarmers(farmers);

    @Test
    @DisplayName("При запросе получаем список всех фермеров")
    void execute() {

        var farmer1 = new FarmerEntity();
        var farmer2 = new FarmerEntity();
        var farmer3 = new FarmerEntity();
        var expected = List.of(farmer1, farmer2, farmer3);
        when(farmers.findAll()).thenReturn(expected);

        var result = service.execute();

        assertEquals(result, expected);
    }
}