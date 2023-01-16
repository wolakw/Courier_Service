package com.example.application.data.repository;

import com.example.application.data.entity.Contact;
import com.example.application.data.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PackageRepository extends JpaRepository<Package, Long> {

    @Query("select p from Package p " +
            "where lower(p.id) like lower(concat('%', :searchTerm, '%')) ")
    List<Package> search(@Param("searchTerm") String searchTerm);
}
