package com.example.demo.api.v1.cows.repository;

import com.example.demo.api.v1.cows.model.entity.CowEntity;
import com.example.demo.api.v1.farmes.model.entity.FarmerEntity;
import com.example.demo.api.v1.farmes.repository.FarmerRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.example.demo.api.v1.cows.model.enums.Color.BLACK;
import static com.example.demo.api.v1.cows.model.enums.Color.RED;
import static com.example.demo.api.v1.cows.model.enums.CowStatus.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class CowRepositoryTest implements BasicIT {

    @Autowired
    private CowRepository cows;

    @Autowired
    private FarmerRepository farmers;

    @AfterEach
    void tearDown() {
        farmers.deleteAll();
    }

    @Test
    @DisplayName("По id фермера находим всех его коров")
    void findAllByFarmerId1() {

        var farmer1 = farmers.save(
                new FarmerEntity(null, "Jek", "Ivanovich", "Duglas")
        );
        var cow11 = cows.save(new CowEntity(null, farmer1.getId(), "Elka", RED, 15, OK)
        );
        var cow12 = cows.save(new CowEntity(null, farmer1.getId(), "Dyna", BLACK, 20, OK)
        );
        var farmer2 = farmers.save(
                new FarmerEntity(null, "Jon", "Anatolyevich", "Bjovi")
        );
        var cow21 = cows.save(new CowEntity(null, farmer2.getId(), "Hana", RED, 16, OK)
        );

        var expected = List.of(cow11, cow12);
        var result = cows.findAllByFarmerId(farmer1.getId());
        assertEquals(result, expected);
    }

    @Test
    @DisplayName("Если у фемрера нет коров, должен вернуться пустой список")
    void findAllByFarmerId2() {

        var farmer1 = farmers.save(
                new FarmerEntity(null, "Jek", "Ivanovich", "Duglas")
        );
        var farmer2 = farmers.save(
                new FarmerEntity(null, "Jon", "Anatolyevich", "Bjovi")
        );
        var cow21 = cows.save(new CowEntity(null, farmer2.getId(), "Hana", RED, 16, OK)
        );

        var result = cows.findAllByFarmerId(farmer1.getId());
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Если фемрера с таким id нет, должен вернуться пустой список")
    void findAllByFarmerId3() {

        var farmer2 = farmers.save(
                new FarmerEntity(null, "Jon", "Anatolyevich", "Bjovi")
        );
        var cow21 = cows.save(new CowEntity(null, farmer2.getId(), "Hana", RED, 16, OK)
        );

        var result = cows.findAllByFarmerId(0);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Если корова с таким id есть, то она будет удалена и метод вернет 1")
    void deleteCowById1() {

        var farmer1 = farmers.save(
                new FarmerEntity(null, "Jon", "Anatolyevich", "Bjovi")
        );
        var cow11 = cows.save(new CowEntity(null, farmer1.getId(), "Hana", RED, 16, OK)
        );
        var cow12 = cows.save(new CowEntity(null, farmer1.getId(), "Dyna", BLACK, 20, OK)
        );
        var farmer2 = farmers.save(
                new FarmerEntity(null, "Jek", "Ivanovich", "Bloh")
        );
        var cow21 = cows.save(new CowEntity(null, farmer2.getId(), "Hana", RED, 16, OK)
        );

        var expected = 1;
        var result = cows.deleteCowById(cow11.getId());
        assertEquals(result, expected);
        assertTrue(cows.findById(cow11.getId()).isEmpty());
    }

    @Test
    @DisplayName("Если коровы с таким id нет, то метод вернет 0")
    void deleteCowById2() {

        var farmer1 = farmers.save(
                new FarmerEntity(null, "Jon", "Anatolyevich", "Bjovi")
        );
        var cow11 = cows.save(new CowEntity(null, farmer1.getId(), "Hana", RED, 16, OK)
        );
        var cow12 = cows.save(new CowEntity(null, farmer1.getId(), "Dyna", BLACK, 20, OK)
        );
        var farmer2 = farmers.save(
                new FarmerEntity(null, "Jek", "Ivanovich", "Bloh")
        );
        var cow21 = cows.save(new CowEntity(null, farmer2.getId(), "Hana", RED, 16, OK)
        );

        var expected = 0;
        var result = cows.deleteCowById(0);
        assertEquals(result, expected);
    }
}