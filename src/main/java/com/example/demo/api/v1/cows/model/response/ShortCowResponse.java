package com.example.demo.api.v1.cows.model.response;

import com.example.demo.api.v1.cows.model.enums.CowStatus;

public record ShortCowResponse(
        Long id,
        String name,
        CowStatus status) {
}
