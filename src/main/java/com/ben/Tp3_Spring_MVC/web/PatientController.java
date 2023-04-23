package com.ben.Tp3_Spring_MVC.web;

import com.ben.Tp3_Spring_MVC.entity.Patient;
import com.ben.Tp3_Spring_MVC.service.IPatientService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@AllArgsConstructor
public class PatientController {
    private final IPatientService service;

    @GetMapping("/")
    public String home(){
        return "redirect:/user/patients";
    }

    @GetMapping("/user/patients")
    public String patients(Model model,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size",defaultValue = "4") int size,
                           @RequestParam(name = "keyword", defaultValue = "") String keyword){
        Page<Patient> patientList = service.search(keyword, PageRequest.of(page, size));
        model.addAttribute("keyword", keyword);
        model.addAttribute("listPatients",patientList.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("pages", new int[patientList.getTotalPages()]);
        return "patients";
    }
    @GetMapping("/admin/deletePatient")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(Long id, String keyword, int page){
        service.deletePatient(id);
        return "redirect:/user/patients?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/admin/formPatient")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String patientForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "formPatient";
    }

    @PostMapping("/admin/savePatient")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addPerson(@Valid Patient patient, Errors errors, Model model) {
        if (errors != null && errors.getErrorCount() > 0) {
            return "formPatient";
        }
        service.savePatient(patient);
        return "redirect:/user/patients";
    }

    @GetMapping("/admin/editPatient")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editPatient(Long id, Model model){
        Patient patient = service.findPatientById(id);
        model.addAttribute("patient",patient);
        return "formPatient";
    }

    @Bean
    private PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
