package com.example.demo.api.v1.cows.service;

import com.example.demo.api.v1.cows.repository.CowRepository;
import com.example.demo.api.v1.cows.model.entity.CowEntity;
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
public class GetAllCows {

    private final CowRepository cowRepository;
    private final ValidateRequest validate;

    public List<CowEntity> execute() {
        log.info("Обработка запроса 'вернуть всех коров'");
        return cowRepository.findAll();
    }
}