package dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class PatientResponseDto {
    private Long id;
    private String fullName;
    private LocalDate dateOfBirth;
    private String gender;
    private String phoneNumber;
    private String email;
    private LocalDate registrationDate;
}
