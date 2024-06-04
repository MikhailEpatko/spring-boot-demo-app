package com.example.demo.api.v1.cows.controller;

import com.example.demo.api.v1.cows.model.request.AddCowRequest;
import com.example.demo.api.v1.cows.model.request.AddDailyLitersRequest;
import com.example.demo.api.v1.cows.model.request.UpdateCowDetailsRequest;
import com.example.demo.api.v1.cows.model.response.FullCowResponse;
import com.example.demo.api.v1.cows.model.response.ShortCowResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "Cows", description = "API для работы с коровами")
public interface CowsApi {

    @Operation(summary = "Запросить всех коров")
    List<ShortCowResponse> getAllCows();

    @Operation(summary = "Запросить всех коров конкретного фермера")
    List<ShortCowResponse> farmerCows(long farmerId);

    @Operation(summary = "Запросить корову по ее ID")
    FullCowResponse cow(long id);

    @Operation(summary = "Добавить корову фермеру")
    void addCowByFarmer(AddCowRequest request);

    @Operation(summary = "Обновить описание коровы")
    void updateCowDetails(UpdateCowDetailsRequest request);

    @Operation(summary = "Удалить корову")
    void deleteCow(long id);

    @Operation(summary = "Зафиксировать удой коровы")
    void addDailyLiters(AddDailyLitersRequest request);
}
