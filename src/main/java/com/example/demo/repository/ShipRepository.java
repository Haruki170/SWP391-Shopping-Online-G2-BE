package com.example.demo.repository;

import com.example.demo.entity.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipRepository extends JpaRepository<Shipper, Long> {
}

