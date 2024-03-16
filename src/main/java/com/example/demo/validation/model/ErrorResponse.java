package com.example.demo.validation.model;

import java.util.List;

public record ErrorResponse(
        List<Violation> errors
) {
}
