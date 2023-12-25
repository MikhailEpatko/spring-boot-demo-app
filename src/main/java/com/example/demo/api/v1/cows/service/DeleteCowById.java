package com.example.demo.api.v1.cows.service;

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
public class DeleteCowById {

    private final CowRepository cowRepository;
    private final ValidateRequest validate;

    public void execute(long id) {
        log.info("Обработка запроса 'удалить корову по ее ID': {}", id);
        if (id < 1)
            throw new BadRequestException("ID коровы не может быть меньше 1");
        var resultDeleted = cowRepository.deleteCowById(id);
        if (resultDeleted == 0) {
            throw new NotFoundException("Корова с ID = " + id + " не найдена");
        }
    }
}
