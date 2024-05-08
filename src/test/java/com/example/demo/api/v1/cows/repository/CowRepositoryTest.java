package com.example.demo.api.v1.cows.repository;

import static com.example.demo.api.v1.cows.model.enums.Color.RED;
import static com.example.demo.api.v1.cows.model.enums.CowStatus.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.example.demo.api.v1.BasicIT;
import com.example.demo.api.v1.cows.model.entity.CowEntity;
import com.example.demo.api.v1.farmes.model.entity.FarmerEntity;
import com.example.demo.api.v1.farmes.repository.FarmerRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
    @DisplayName("По id фермера должны найти всех его коров")
    void findAllByFarmerId1() {
        var farmer1 = farmers.save(
                new FarmerEntity(null, "Mikl", "Ivanovich", "Duglas")
        );
        var cow11 = cows.save(
                new CowEntity(null, farmer1.getId(), "Elka", RED, 15, OK)
        );
        var cow12 = cows.save(
                new CowEntity(null, farmer1.getId(), "Elka", RED, 15, OK)
        );

        var farmer2 = farmers.save(
                new FarmerEntity(null, "Jon", "Anatolyevich", "Bjovi")
        );
        var cow21 = cows.save(
                new CowEntity(null, farmer2.getId(), "Elka", RED, 15, OK)
        );
        var expected = List.of(cow11, cow12);

        var result = cows.findAllByFarmerId(farmer1.getId());

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Если у фермера нет коров, должен вернуться пустой список")
    void findAllByFarmerId2() {
        var farmer1 = farmers.save(
                new FarmerEntity(null, "Mikl", "Ivanovich", "Duglas")
        );
        var farmer2 = farmers.save(
                new FarmerEntity(null, "Jon", "Anatolyevich", "Bjovi")
        );
        var cow21 = cows.save(
                new CowEntity(null, farmer2.getId(), "Elka", RED, 15, OK)
        );

        var result = cows.findAllByFarmerId(farmer1.getId());

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Если фермера c таким id нет, должен вернуться пустой список")
    void findAllByFarmerId3() {
        var farmer2 = farmers.save(
                new FarmerEntity(null, "Jon", "Anatolyevich", "Bjovi")
        );
        var cow21 = cows.save(
                new CowEntity(null, farmer2.getId(), "Elka", RED, 15, OK)
        );

        var result = cows.findAllByFarmerId(0);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Если корова с таким id есть, то она будет удалена и метод вернёт 1")
    void deleteCowById1() {
        var farmer1 = farmers.save(
                new FarmerEntity(null, "Mikl", "Ivanovich", "Duglas")
        );
        var cow11 = cows.save(
                new CowEntity(null, farmer1.getId(), "Elka", RED, 15, OK)
        );
        var cow12 = cows.save(
                new CowEntity(null, farmer1.getId(), "Elka", RED, 15, OK)
        );

        var farmer2 = farmers.save(
                new FarmerEntity(null, "Jon", "Anatolyevich", "Bjovi")
        );
        var cow21 = cows.save(
                new CowEntity(null, farmer2.getId(), "Elka", RED, 15, OK)
        );
        var expected = 1;

        var result = cows.deleteCowById(cow11.getId());

        assertEquals(expected, result);
        assertTrue(cows.findById(cow11.getId()).isEmpty());
    }

    @Test
    @DisplayName("Если коровы с таким id нет, то метод вернёт 0")
    void deleteCowById2() {
        var farmer1 = farmers.save(
                new FarmerEntity(null, "Mikl", "Ivanovich", "Duglas")
        );
        var cow11 = cows.save(
                new CowEntity(null, farmer1.getId(), "Elka", RED, 15, OK)
        );
        var cow12 = cows.save(
                new CowEntity(null, farmer1.getId(), "Elka", RED, 15, OK)
        );

        var farmer2 = farmers.save(
                new FarmerEntity(null, "Jon", "Anatolyevich", "Bjovi")
        );
        var cow21 = cows.save(
                new CowEntity(null, farmer2.getId(), "Elka", RED, 15, OK)
        );
        var expected = 0;

        var result = cows.deleteCowById(0L);

        assertEquals(expected, result);
    }
}