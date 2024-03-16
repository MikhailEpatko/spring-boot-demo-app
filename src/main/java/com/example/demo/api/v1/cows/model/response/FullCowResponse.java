package com.example.demo.api.v1.cows.model.response;

import com.example.demo.api.v1.cows.model.enums.Color;
import com.example.demo.api.v1.cows.model.enums.CowStatus;

public record FullCowResponse(
        Long id,
        Long farmerId,
        String name,
        Color color,
        int liters,
        CowStatus status
) {
}
