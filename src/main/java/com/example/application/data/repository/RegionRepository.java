package com.example.application.data.repository;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {
}
