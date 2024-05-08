package com.example.demo.api.v1.farmes.controller;

import com.example.demo.api.v1.farmes.model.entity.FarmerEntity;
import com.example.demo.api.v1.farmes.model.request.AddFarmerRequest;
import com.example.demo.api.v1.farmes.model.request.UpdateFarmerDetailsRequest;
import com.example.demo.api.v1.farmes.model.response.FarmerResponse;
import com.example.demo.api.v1.farmes.service.AddFarmer;
import com.example.demo.api.v1.farmes.service.DeleteFarmerById;
import com.example.demo.api.v1.farmes.service.GetAllFarmers;
import com.example.demo.api.v1.farmes.service.GetFarmerById;
import com.example.demo.api.v1.farmes.service.UpdateFarmer;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/farmers")
@RequiredArgsConstructor
public class FarmersController implements FarmersApi {

    private final AddFarmer addFarmer;
    private final GetAllFarmers getAllFarmers;
    private final GetFarmerById getFarmerById;
    private final DeleteFarmerById deleteFarmerById;
    private final UpdateFarmer updateFarmer;

    @GetMapping("/")
    @Override
    public List<FarmerResponse> getAllFarmers() {
        return getAllFarmers.execute()
                .stream()
                .map((FarmerEntity::toResponse))
                .toList();
    }

    @GetMapping("/{id}")
    @Override
    public FarmerResponse farmer(@PathVariable long id) {
        return getFarmerById.execute(id).toResponse();
    }

    @PostMapping("/")
    @Override
    public void addFarmer(@RequestBody AddFarmerRequest request) {
        addFarmer.execute(request);
    }

    @PutMapping("/")
    @Override
    public void updateFarmerDetails(@RequestBody UpdateFarmerDetailsRequest request) {
        updateFarmer.execute(request);
    }

    @DeleteMapping("/{id}")
    @Override
    public void deleteFarmer(@PathVariable long id) {
        deleteFarmerById.execute(id);
    }
}
