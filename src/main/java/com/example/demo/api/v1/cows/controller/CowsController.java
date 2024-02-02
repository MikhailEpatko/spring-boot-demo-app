package com.example.demo.api.v1.cows.controller;

import com.example.demo.api.v1.cows.model.entity.CowEntity;
import com.example.demo.api.v1.cows.model.request.AddCowRequest;
import com.example.demo.api.v1.cows.model.request.UpdateCowDetailsRequest;
import com.example.demo.api.v1.cows.model.response.FullCowResponse;
import com.example.demo.api.v1.cows.model.response.ShortCowResponse;
import com.example.demo.api.v1.cows.service.AddCowToFarmer;
import com.example.demo.api.v1.cows.service.DeleteCowById;
import com.example.demo.api.v1.cows.service.GetAllCows;
import com.example.demo.api.v1.cows.service.GetAllCowsFarmerByFarmerId;
import com.example.demo.api.v1.cows.service.GetCowById;
import com.example.demo.api.v1.cows.service.UpdateCow;
import java.util.List;
import java.util.stream.Collectors;
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
public class CowsController implements CowsApi {

    private final GetAllCows allCows;
    private final GetAllCowsFarmerByFarmerId cowsFarmer;
    private final GetCowById cowById;
    private final AddCowToFarmer addCowToFarmer;
    private final UpdateCow updateCow;
    private final DeleteCowById deleteCowById;

    @GetMapping("/cows")
    @Override
    public List<ShortCowResponse> getAllCows() {
        return allCows.execute()
                .stream()
                .map(CowEntity::toShortResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{farmerId}/cows")
    @Override
    public List<ShortCowResponse> farmerCows(@PathVariable long farmerId) {
        return cowsFarmer.execute(farmerId)
                .stream()
                .map(CowEntity::toShortResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/cows/{id}")
    @Override
    public FullCowResponse cow(@PathVariable long id) {
        return cowById.execute(id).toFullResponse();
    }

    @PostMapping("/cows")
    @Override
    public void addCowByFarmer(@RequestBody AddCowRequest request) {
        addCowToFarmer.execute(request);
    }

    @PutMapping("/cows")
    @Override
    public void updateCowDetails(@RequestBody UpdateCowDetailsRequest request) {
        updateCow.execute(request);
    }

    @DeleteMapping("/cows/{id}")
    @Override
    public void deleteCow(@PathVariable long id) {
        deleteCowById.execute(id);
    }
}
