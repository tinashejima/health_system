package com.health_project.server.services;

import com.health_project.server.dto.PatientRegistrationDto;
import com.health_project.server.dto.PatientResponseDto;

public interface PatientService {
    PatientResponseDto registerPatient(PatientRegistrationDto registrationDto);
    PatientResponseDto getPatientByPatientId(String patientId);
}