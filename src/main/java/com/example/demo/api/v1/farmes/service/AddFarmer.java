package com.example.demo.api.v1.farmes.service;

import com.example.demo.api.v1.farmes.model.request.AddFarmerRequest;
import com.example.demo.api.v1.farmes.repository.FarmerRepository;
import com.example.demo.validation.service.ValidateRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AddFarmer {

    private final FarmerRepository farmerRepository;
    private final ValidateRequest validateRequest;

    public void execute(AddFarmerRequest request) {
        log.info("Обработка запроса 'добавить фермера': {}", request);
        validateRequest.single(request);
        farmerRepository.save(request.toEntity());
    }

}
