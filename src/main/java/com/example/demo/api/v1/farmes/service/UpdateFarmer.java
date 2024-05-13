package com.example.demo.api.v1.farmes.service;

import com.example.demo.api.v1.farmes.model.request.UpdateFarmerDetailsRequest;
import com.example.demo.api.v1.farmes.repository.FarmerRepository;
import com.example.demo.common.exceptions.NotFoundException;
import com.example.demo.validation.service.ValidateRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UpdateFarmer {

    private final FarmerRepository farmerRepository;
    private final ValidateRequest validateRequest;

    public void execute(UpdateFarmerDetailsRequest request) {
        log.info("Обработка запроса 'обновить данные фермера': {}", request);
        validateRequest.single(request);
        var toUpdateFarmer = farmerRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException("Фермер с ID = " + request.getId() + " не найден"));
        toUpdateFarmer.update(request);
        farmerRepository.save(toUpdateFarmer);
    }
}
