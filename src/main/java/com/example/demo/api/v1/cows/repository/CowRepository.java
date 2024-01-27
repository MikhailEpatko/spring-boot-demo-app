package com.example.demo.api.v1.cows.repository;

import com.example.demo.api.v1.cows.model.entity.CowEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CowRepository extends JpaRepository<CowEntity, Long> {

    List<CowEntity> findAllByFarmerId(long farmerId);

    @Modifying
    int deleteByIdEquals(@Param("id") long id);
}