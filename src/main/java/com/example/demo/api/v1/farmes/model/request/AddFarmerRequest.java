package com.example.demo.api.v1.farmes.model.request;

import com.example.demo.api.v1.farmes.model.entity.FarmerEntity;
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
public class AddFarmerRequest {

    @NotNull(message = "Не может быть null")
    @Min(value = 1L, message = "Не может быть меньше 1")
    private Long id;

    @NotBlank(message = "Не может быть null или состоять из пробельных символов")
    private String firstName;

    @NotBlank(message = "Не может быть null или состоять из пробельных символов")
    private String middleName;

    @NotBlank(message = "Не может быть null или состоять из пробельных символов")
    private String lastName;

    public FarmerEntity toEntity() {
        return new FarmerEntity(
                null,
                firstName,
                middleName,
                lastName
        );
    }

}
