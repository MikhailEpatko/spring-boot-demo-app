package com.example.demo.api.v1.farmes.controller;

import com.example.demo.api.v1.farmes.model.request.AddFarmerRequest;
import com.example.demo.api.v1.farmes.model.request.UpdateFarmerDetailsRequest;
import com.example.demo.api.v1.farmes.model.response.FarmerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "Farmers", description = "API для работы с фермерами")
public interface FarmersApi {

    @Operation(summary = "Запросить всех фермеров")
    List<FarmerResponse> getAllFarmers();

    @Operation(summary = "Запросить фермера по его ID")
    FarmerResponse farmer(long id);

    @Operation(summary = "Добавить фермера")
    void addFarmer(AddFarmerRequest request);

    @Operation(summary = "Обновить описание фермера")
    void updateFarmerDetails(UpdateFarmerDetailsRequest request);

    @Operation(summary = "Удалить фермера")
    void deleteFarmer(long id);
}
