package com.example.demo.api.v1.cows.service;

import static com.example.demo.api.v1.cows.model.enums.CowStatus.DEAD;
import static com.example.demo.api.v1.cows.model.enums.CowStatus.SOLD;

import com.example.demo.api.v1.cows.model.request.UpdateCowDetailsRequest;
import com.example.demo.api.v1.cows.repository.CowRepository;
import com.example.demo.common.exceptions.BadRequestException;
import com.example.demo.common.exceptions.NotFoundException;
import com.example.demo.validation.service.ValidateRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UpdateCow {

    private final CowRepository cowRepository;
    private final ValidateRequest validate;

    public void execute(UpdateCowDetailsRequest request) {
        log.info("Обработка запроса 'обновить данные коровы': {}", request);
        validate.single(request);
        var existentCow = cowRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException("Корова с ID = " + request.getId() + " не найдена"));
        if (canUpdate(request)) {
            existentCow.update(request);
            cowRepository.save(existentCow);
        } else {
            throw new BadRequestException("Данные коровы с ID = " + request.getId() + "не могут быть обновлены,"
                    + "т.к. корова уже продана или умерла (см. статус коровы)");
        }
    }

    public boolean canUpdate(UpdateCowDetailsRequest request) {
        return cowRepository.findById(request.getId()).map(entity -> !entity.getStatus().equals(DEAD)
                && !entity.getStatus().equals(SOLD)).orElse(false);
    }
}