package dto;


import lombok.Data;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
@Data
public class PatientRegistrationDto {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String address;
    private String phoneNumber;
    private String email;
    private String nationalId;
}
