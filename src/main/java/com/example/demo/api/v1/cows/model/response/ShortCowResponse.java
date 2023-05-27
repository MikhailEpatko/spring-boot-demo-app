package com.example.demo.api.v1.cows.model.response;

import com.example.demo.api.v1.cows.model.enums.Status;

public record ShortCowResponse(
        Long id,
        String name,
        Status status) {
}
