package controllers;


import dto.PatientRegistrationDto;
import dto.PatientResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import services.PatientService;

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


}
