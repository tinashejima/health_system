package com.health_project.server.services;


import com.health_project.server.dto.PatientRegistrationDto;
import com.health_project.server.dto.PatientResponseDto;
import com.health_project.server.entities.PatientEntity;
import com.health_project.server.handleException.DuplicatePatientException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.health_project.server.repositories.PatientRepository;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public PatientResponseDto registerPatient(PatientRegistrationDto registrationDto) {
        // Check for duplicates
        if (patientRepository.existsByNationalId(registrationDto.getNationalId())) {
            throw new DuplicatePatientException("Patient with this national ID already exists");
        }

        if (registrationDto.getEmail() != null &&
                patientRepository.existsByEmail(registrationDto.getEmail())) {
            throw new DuplicatePatientException("Patient with this email already exists");
        }

        if (patientRepository.existsByPhoneNumber(registrationDto.getPhoneNumber())) {
            throw new DuplicatePatientException("Patient with this phone number already exists");
        }

        // Create a new entity instead of using modelMapper directly
        PatientEntity patient = new PatientEntity();
        // Manually set the fields
        patient.setFirstName(registrationDto.getFirstName());
        patient.setLastName(registrationDto.getLastName());
        patient.setDateOfBirth(registrationDto.getDateOfBirth());
        patient.setGender(registrationDto.getGender());
        patient.setAddress(registrationDto.getAddress());
        patient.setPhoneNumber(registrationDto.getPhoneNumber());
        patient.setEmail(registrationDto.getEmail());
        patient.setNationalId(registrationDto.getNationalId());
        // No need to set patientId or personId as they will be auto-generated


        // Save to database
        PatientEntity savedPatient = patientRepository.save(patient);

        // Map entity to response DTO
        PatientResponseDto responseDto = modelMapper.map(savedPatient, PatientResponseDto.class);

        return responseDto;
    }

    // Your existing registerPatient method...

    @Override
    public PatientResponseDto getPatientByPatientId(String patientId) {
        PatientEntity patient = patientRepository.findByPatientId(patientId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Patient not found with ID: " + patientId
            ));
        
        return modelMapper.map(patient, PatientResponseDto.class);
    }
}