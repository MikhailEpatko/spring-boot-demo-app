package com.example.demo.api.v1.cows.service;

import com.example.demo.api.v1.cows.model.entity.CowEntity;
import com.example.demo.api.v1.cows.repository.CowRepository;
import com.example.demo.common.exceptions.BadRequestException;
import com.example.demo.validation.service.ValidateRequest;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class GetAllCowsFarmerByFarmerId {

    private final CowRepository cowRepository;
    private final ValidateRequest validate;

    public List<CowEntity> execute(long farmerId) {
        log.info("Обработка запроса 'вернуть всех коров конкретного фермера': {}", farmerId);
        if (farmerId < 1)
            throw new BadRequestException("ID фермера не может быть меньше 1");
        return cowRepository.findAllByFarmerId(farmerId);
    }
}
