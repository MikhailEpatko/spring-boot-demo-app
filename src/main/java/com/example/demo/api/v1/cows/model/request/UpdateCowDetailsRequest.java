package com.example.demo.api.v1.cows.model.request;

import com.example.demo.api.v1.cows.model.enums.Color;
import com.example.demo.api.v1.cows.model.enums.CowStatus;
import com.example.demo.validation.annotation.NullableNotBlank;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateCowDetailsRequest {

    @NotNull(message = "Не может быть null")
    @Min(value = 1L, message = "Не может быть меньше 1")
    private Long id;

    @Min(value = 1L, message = "Не может быть меньше 1")
    private Long farmerId;

    @NullableNotBlank
    private String name;

    private Color color;

    @Min(value = 0, message = "Не может быть меньше 0")
    private Integer liters;

    private CowStatus status;
}
