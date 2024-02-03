package com.example.demo.api.v1.farmes.repository;

import com.example.demo.api.v1.farmes.model.entity.FarmerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmerRepository extends JpaRepository<FarmerEntity, Long> {
}
