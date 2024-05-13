package com.example.demo.api.v1.farmes.model.entity;

import com.example.demo.api.v1.farmes.model.request.UpdateFarmerDetailsRequest;
import com.example.demo.api.v1.farmes.model.response.FarmerResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "farmer")
@AllArgsConstructor
@NoArgsConstructor
public class FarmerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    public FarmerResponse toResponse() {
        return new FarmerResponse(id, firstName, middleName, lastName);
    }

    public void update(UpdateFarmerDetailsRequest request) {
        if (request.getFirstName() != null) {
            firstName = request.getFirstName();
        }
        if (request.getMiddleName() != null) {
            middleName = request.getMiddleName();
        }
        if (request.getLastName() != null) {
            lastName = request.getLastName();
        }
    }
}
