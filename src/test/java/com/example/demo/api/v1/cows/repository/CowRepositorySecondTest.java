package com.example.demo.api.v1.cows.repository;

import org.junit.jupiter.api.Test;
import com.example.demo.api.v1.cows.model.entity.CowEntity;
import com.example.demo.api.v1.farmes.model.entity.FarmerEntity;
import com.example.demo.api.v1.farmes.repository.FarmerRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static com.example.demo.api.v1.cows.model.enums.Color.BLACK;
import static com.example.demo.api.v1.cows.model.enums.Color.RED;
import static com.example.demo.api.v1.cows.model.enums.CowStatus.OK;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class CowRepositorySecondTest implements BasicIT {

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
    @DisplayName("по заданному id находим и удаляем корову")
    void whenDeleteCowById() {

        List<FarmerEntity> farmerEntities = List.of(
                new FarmerEntity(1L, "Jek", "Ivanovich", "Duglas"),
                new FarmerEntity(2L, "Jon", "Anatolyevich", "Bjovi")
        );
        farmers.saveAll(farmerEntities);

        List<CowEntity> cowEntities = List.of(
                new CowEntity(1L, 1L, "Elka", RED, 15, OK),
                new CowEntity(2L, 2L, "Dyna", BLACK, 20, OK),
                new CowEntity(3L, 1L, "Hana", RED, 16, OK)
        );
        cows.saveAll(cowEntities);
        cows.deleteCowById(2L);

        assertThat(cows.count()).isEqualTo(2);

    }
}