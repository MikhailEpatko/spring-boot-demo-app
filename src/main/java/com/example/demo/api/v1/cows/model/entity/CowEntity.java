package com.example.demo.api.v1.cows.model.entity;

import com.example.demo.api.v1.cows.model.enums.Color;
import com.example.demo.api.v1.cows.model.enums.CowStatus;
import com.example.demo.api.v1.cows.model.request.UpdateCowDetailsRequest;
import com.example.demo.api.v1.cows.model.response.FullCowResponse;
import com.example.demo.api.v1.cows.model.response.ShortCowResponse;
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
@Table(name = "farmer_cow")
@AllArgsConstructor
@NoArgsConstructor
public class CowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "farmer_id")
    private Long farmerId;

    @Column(name = "name")
    private String name;

    @Column(name = "color")
    private Color color;

    @Column(name = "liters")
    private int liters;

    @Column(name = "status")
    private CowStatus status;

    public ShortCowResponse toShortResponse() {
        return new ShortCowResponse(id, name, status);
    }

    public FullCowResponse toFullResponse() {
        return new FullCowResponse(id, farmerId, name, color, liters, status);
    }

    public void update(UpdateCowDetailsRequest request) {
        if (request.getFarmerId() != null)
            farmerId = request.getFarmerId();
        if (request.getName() != null)
            name = request.getName();
        if (request.getColor() != null)
            color = request.getColor();
        if (request.getLiters() != null)
            liters = request.getLiters();
        if (request.getStatus() != null)
            status = request.getStatus();
    }
}
