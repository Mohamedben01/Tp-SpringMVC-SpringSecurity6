package com.ben.Tp3_Spring_MVC.service;

import com.ben.Tp3_Spring_MVC.entity.Patient;
import com.ben.Tp3_Spring_MVC.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements IPatientService {

    private final PatientRepository patientRepository;

    @Override
    public List<Patient> patients() {
        return patientRepository.findAll();
    }

    @Override
    public Page<Patient> patientByPagination(PageRequest pageRequest) {
        return patientRepository.findAll(pageRequest);
    }

    @Override
    public Page<Patient> search(String nom, Pageable pageable) {
        return patientRepository.findByNomContains(nom, pageable);
    }


    @Override
    public void deletePatient(Long patientId) {
        patientRepository.deleteById(patientId);
    }

    @Override
    public void savePatient(Patient patient) {
        patientRepository.save(patient);
    }

    @Override
    public Patient findPatientById(Long id) {
        return patientRepository.findById(id).orElse(null);
    }
}
