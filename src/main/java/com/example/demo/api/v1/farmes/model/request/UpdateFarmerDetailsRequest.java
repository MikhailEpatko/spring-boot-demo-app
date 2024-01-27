package com.example.demo.api.v1.farmes.model.request;

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
public class UpdateFarmerDetailsRequest {

    @NotNull(message = "Не может быть null")
    @Min(value = 1L, message = "Не может быть меньше 1")
    private Long id;

    @NullableNotBlank
    private String firstName;

    @NullableNotBlank
    private String middleName;

    @NullableNotBlank
    private String lastName;
}
