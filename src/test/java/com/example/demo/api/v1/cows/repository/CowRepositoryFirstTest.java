package com.example.demo.api.v1.cows.repository;

import com.example.demo.api.v1.cows.model.entity.CowEntity;
import com.example.demo.api.v1.farmes.model.entity.FarmerEntity;
import com.example.demo.api.v1.farmes.repository.FarmerRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.Collections;
import java.util.List;

import static com.example.demo.api.v1.cows.model.enums.Color.BLACK;
import static com.example.demo.api.v1.cows.model.enums.Color.RED;
import static com.example.demo.api.v1.cows.model.enums.CowStatus.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;


class CowRepositoryFirstTest implements BasicIT {

    @Autowired
    private CowRepository cows;

    @Autowired
    private FarmerRepository farmers;

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        cows.deleteAll();
    }

    @Test
    @DisplayName("по заданному id находим всех коров конкретного фермера")
    void whenFindAllByFarmerId() {

        List<FarmerEntity> farmerEntities = List.of(
                new FarmerEntity(1L, "Mikl", "Ivanovich", "Duglas"),
                new FarmerEntity(2L, "Jon", "Anatolyevich", "Bjovi"),
                new FarmerEntity(3L, "Ivan", "Ivanovich", "Ivanov")
        );
        farmers.saveAll(farmerEntities);

        List<CowEntity> cowEntities = List.of(
                new CowEntity(1L, 1L, "Elka", RED, 15, OK),
                new CowEntity(2L, 2L, "Dyna", BLACK, 20, OK),
                new CowEntity(3L, 1L, "Hana", RED, 16, OK)
        );
        cows.saveAll(cowEntities);

        var resultList = List.of(new CowEntity(1L, 1L, "Elka", RED, 15, OK),
                new CowEntity(3L, 1L, "Hana", RED, 16, OK));

        assertEquals(cows.findAllByFarmerId(1L), resultList);
        assertEquals(cows.findAllByFarmerId(2L),
                List.of(new CowEntity(2L, 2L, "Dyna", BLACK, 20, OK)));
        assertEquals(cows.findAllByFarmerId(3L), Collections.emptyList());
    }
}