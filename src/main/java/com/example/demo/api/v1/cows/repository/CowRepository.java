package com.example.demo.api.v1.cows.repository;

import com.example.demo.api.v1.cows.model.entity.CowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CowRepository extends JpaRepository<CowEntity, Long> {

    List<CowEntity> findAllByFarmerId(long farmerId);

    @Transactional
    @Modifying
    @Query(
        nativeQuery = true,
        value = "delete from farmer_cow where id = :id")
    int deleteCowById(long id);
}