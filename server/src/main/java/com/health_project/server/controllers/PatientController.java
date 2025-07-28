package com.health_project.server.controllers;


import com.health_project.server.dto.PatientRegistrationDto;
import com.health_project.server.dto.PatientResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.health_project.server.services.PatientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/register")
    public ResponseEntity<PatientResponseDto> registerPatient(
            @Valid @RequestBody PatientRegistrationDto registrationDto) {
        PatientResponseDto responseDto = patientService.registerPatient(registrationDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<PatientResponseDto> getPatientById(@PathVariable String patientId) {
        PatientResponseDto patient = patientService.getPatientByPatientId(patientId);
        return ResponseEntity.ok(patient);
    }
}