package com.example.demo.api.v1.farmes.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.demo.api.v1.BasicIT;
import com.example.demo.api.v1.farmes.model.entity.FarmerEntity;
import com.example.demo.api.v1.farmes.model.request.UpdateFarmerDetailsRequest;
import com.example.demo.api.v1.farmes.repository.FarmerRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FarmersControllerTest extends BasicIT {

    @Autowired
    private FarmerRepository farmers;

    @Autowired
    private TestRestTemplate restTemplate;

    @AfterEach
    void clear() {
        farmers.deleteAll();
    }

    @Test
    void testConnection() {
        assertTrue(postgres.isRunning());
    }

    @Test
    void getAllFarmers() {
        var farmer1 = farmers.save(
                new FarmerEntity(
                        null,
                        "firstName",
                        "middleName",
                        "lastName"
                )
        );
        var farmer2 = farmers.save(
                new FarmerEntity(
                        null,
                        "secondName",
                        "MiddleName",
                        "LastName"
                )
        );
        ResponseEntity<List<FarmerEntity>> response = restTemplate.exchange(
                "/v1/farmers/farmers",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<FarmerEntity>>() {
                }
        );
        List<FarmerEntity> farmersList = response.getBody();
        assertThat(farmersList, hasSize(2));
        assertThat(farmersList.get(1).getFirstName(), is("secondName"));
    }

    @Test
    void farmerById() {
        var farmer1 = farmers.save(
                new FarmerEntity(
                        null,
                        "firstName",
                        "middleName",
                        "lastName"
                )
        );
        var farmerResponse = restTemplate.getForObject(
                "/v1/farmers/farmers/{id}",
                FarmerEntity.class,
                farmer1.getId()
        );
        assertThat(farmerResponse.getFirstName(), is("firstName"));
    }

    @Test
    void addFarmer() {
        var farmer1 = farmers.save(
                new FarmerEntity(
                        null,
                        "firstName",
                        "middleName",
                        "lastName"
                )
        );

        var response = restTemplate.postForEntity(
                "/v1/farmers/farmers",
                farmer1,
                FarmerEntity.class
        );

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertEquals(farmers.findById(farmer1.getId()), Optional.of(farmer1));
    }

    @Test
    void updateFarmerDetails() {
        var farmer1 = farmers.save(
                new FarmerEntity(
                        null,
                        "firstName",
                        "middleName",
                        "lastName"
                )
        );
        var farmerDetails = new UpdateFarmerDetailsRequest(
                null,
                "superName",
                "middleName",
                "lastName"
        );
        farmer1.update(farmerDetails);

        HttpEntity<FarmerEntity> entity = new HttpEntity<FarmerEntity>(farmer1);

        ResponseEntity<FarmerEntity> response = restTemplate.exchange(
                "/v1/farmers/farmers",
                HttpMethod.PUT,
                entity,
                FarmerEntity.class,
                farmer1.getId()
        );

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertEquals(farmer1.getFirstName(), "superName");
    }

    @Test
    void deleteFarmer() {
        var farmer1 = farmers.save(
                new FarmerEntity(
                        null,
                        "firstName",
                        "middleName",
                        "lastName"
                )
        );

        ResponseEntity<FarmerEntity> response = restTemplate.exchange(
                "/v1/farmers/farmers/{id}",
                HttpMethod.DELETE,
                null,
                FarmerEntity.class,
                farmer1.getId()
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(farmers.count(), is(0L));
    }
}