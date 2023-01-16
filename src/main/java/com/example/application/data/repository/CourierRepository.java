package com.example.application.data.repository;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourierRepository extends JpaRepository<Courier, Long> {
}
