package com.ben.Tp3_Spring_MVC.repository;

import com.ben.Tp3_Spring_MVC.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query("SELECT p FROM Patient p WHERE p.nom like :keyword")
    Page<Patient> search(@Param("keyword") String keyword, Pageable pageable);

    Page<Patient> findByNomContains(String nom, Pageable pageable);
}
