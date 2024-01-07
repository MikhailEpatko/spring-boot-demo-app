package com.example.demo.api.v1.cows.service;

import com.example.demo.api.v1.cows.repository.CowRepository;
import com.example.demo.api.v1.cows.model.request.AddCowRequest;
import com.example.demo.validation.service.ValidateRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AddCowToFarmer {

    private final CowRepository cowRepository;
    private final ValidateRequest validate;

    public void execute(AddCowRequest request) {
        log.info("Обработка запроса 'добавить корову фермеру': {}", request);
        validate.single(request);
        cowRepository.save(request.toEntity());
    }
}
