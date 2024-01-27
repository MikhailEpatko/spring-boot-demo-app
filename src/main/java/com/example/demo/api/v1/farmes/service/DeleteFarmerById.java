package com.example.demo.api.v1.farmes.service;

import com.example.demo.api.v1.farmes.repository.FarmerRepository;
import com.example.demo.common.exceptions.BadRequestException;
import com.example.demo.common.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class DeleteFarmerById {

    private final FarmerRepository farmerRepository;

    public void execute(long id) {
        log.info("Обработка запроса 'удалить фермера по его ID': {}", id);
        if (id < 1) {
            throw new BadRequestException("ID фермера не может быть меньше 1");
        }
        var deleted = farmerRepository.findById(id);
        if (deleted.isEmpty()) {
            throw new NotFoundException("Фермер с ID = " + id + " не найден");
        } else farmerRepository.deleteById(id);
    }
}
