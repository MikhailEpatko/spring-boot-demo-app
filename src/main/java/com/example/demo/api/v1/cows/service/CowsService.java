package com.example.demo.api.v1.cows.service;

import com.example.demo.api.v1.cows.CowRepository;
import com.example.demo.api.v1.cows.model.entity.CowEntity;
import com.example.demo.api.v1.cows.model.request.AddCowRequest;
import com.example.demo.api.v1.cows.model.request.UpdateCowDetailsRequest;
import com.example.demo.common.exceptions.BadRequestException;
import com.example.demo.common.exceptions.NotFoundException;
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
public class CowsService {

    private final CowRepository cowRepository;
    private final ValidateRequest validate;


    public List<CowEntity> all() {
        log.info("Обработка запроса 'вернуть всех коров'");
        return cowRepository.findAll();
    }

    public List<CowEntity> byFarmer(long farmerId) {
        log.info("Обработка запроса 'вернуть всех коров конкретного фермера': {}", farmerId);
        if (farmerId < 1)
            throw new BadRequestException("ID фермера не может быть меньше 1");
        return cowRepository.findAllByFarmerId(farmerId);
    }

    public CowEntity byId(long id) {
        log.info("Обработка запроса 'найти корову по ее ID': {}", id);
        if (id < 1)
            throw new BadRequestException("ID коровы не может быть меньше 1");
        return cowRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Корова с ID = " + id + " не найдена"));
    }

    public void add(AddCowRequest request) {
        log.info("Обработка запроса 'добавить корову фермеру': {}", request);
        validate.single(request);
        cowRepository.save(request.toEntity());
    }

    public void updateCowDetails(UpdateCowDetailsRequest request) {
        log.info("Обработка запроса 'обновить данные коровы': {}", request);
        validate.single(request);
        var existentCow = cowRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException("Корова с ID = " + request.getId() + " не найдена"));
        existentCow.update(request);
        cowRepository.save(existentCow);
    }

    public void deleteCow(long id) {
        log.info("Обработка запроса 'удалить корову по ее ID': {}", id);
        if (id < 1)
            throw new BadRequestException("ID коровы не может быть меньше 1");
        var existentCow = cowRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Корова с ID = " + id + " не найдена"));
        cowRepository.delete(existentCow);
    }
}
