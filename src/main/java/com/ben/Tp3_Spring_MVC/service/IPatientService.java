package com.ben.Tp3_Spring_MVC.service;

import com.ben.Tp3_Spring_MVC.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IPatientService {

    List<Patient> patients();
    Page<Patient> patientByPagination(PageRequest pageRequest);
    Page<Patient> search(String nom, Pageable pageable);
    void deletePatient(Long patientId);

    void savePatient(Patient patient);

    Patient findPatientById(Long id);
}
