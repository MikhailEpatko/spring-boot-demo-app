package com.example.demo.api.v1.cows.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class AddDailyLitersRequest {

    @NotNull(message = "Не может быть null")
    @Min(value = 1L, message = "Не может быть меньше 1")
    private Long id;

    @NotNull(message = "Дата не может быть null")
    private LocalDateTime date = LocalDateTime.now();

    @Min(value = 0, message = "Не может быть меньше 0")
    @Max(value = 20, message = "Не может быть больше 20")
    private int liters;
}
