package services;

import dto.PatientRegistrationDto;
import dto.PatientResponseDto;

public interface PatientService {

    PatientResponseDto registerPatient(PatientRegistrationDto registrationDto);
}
