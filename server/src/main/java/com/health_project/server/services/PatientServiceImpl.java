package services;


import dto.PatientRegistrationDto;
import dto.PatientResponseDto;
import entities.PatientEntity;
import handleException.DuplicatePatientException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import repositories.PatientRepository;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService{
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

        // Map DTO to entity
        PatientEntity patient = modelMapper.map(registrationDto, PatientEntity.class);

        // Save to database
        PatientEntity savedPatient = patientRepository.save(patient);

        // Map entity to response DTO
        PatientResponseDto responseDto = modelMapper.map(savedPatient, PatientResponseDto.class);
        responseDto.setFullName(savedPatient.getFirstName() + " " + savedPatient.getLastName());

        return responseDto;
    }

}
