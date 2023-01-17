package com.example.application.data.repository;

import com.example.application.data.entity.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourierRepository extends JpaRepository<Courier, Long> {

    @Query("select c from Courier c " +
            "where lower(c.firstName) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.lastName) like lower(concat('%', :searchTerm, '%'))")
    List<Courier> search(@Param("searchTerm") String searchTerm);
}
