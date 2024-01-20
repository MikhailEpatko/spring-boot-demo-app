package com.example.demo.api.v1.cows.model.request;

import com.example.demo.api.v1.cows.model.entity.CowEntity;
import com.example.demo.api.v1.cows.model.enums.Color;
import com.example.demo.api.v1.cows.model.enums.CowStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class AddCowRequest {

    @NotNull(message = "Не может быть null")
    @Min(value = 1L, message = "Не может быть меньше 1")
    private Long farmerId;

    @NotBlank(message = "Не может быть null или состоять из пробельных символов")
    private String name;

    @NotNull(message = "Не может быть null")
    private Color color;

    @Min(value = 0, message = "Не может быть меньше 0")
    private int liters;

    @NotNull(message = "Не может быть null")
    private CowStatus status;

    public CowEntity toEntity() {
        return new CowEntity(
                null,
                farmerId,
                name,
                color,
                liters,
                status
        );
    }
}
