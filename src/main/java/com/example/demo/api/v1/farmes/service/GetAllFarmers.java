package com.example.demo.api.v1.farmes.service;

import com.example.demo.api.v1.farmes.model.entity.FarmerEntity;
import com.example.demo.api.v1.farmes.repository.FarmerRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class GetAllFarmers {

    private final FarmerRepository farmerRepository;

    public List<FarmerEntity> execute() {
        log.info("Обработка запроса 'вернуть всех фермеров'");
        return farmerRepository.findAll();
    }
}
