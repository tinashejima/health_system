package com.health_project.server.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientResponseDto {
    private String patientId;
    private String personId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String address;
    private String phoneNumber;
    private String email;
    private String nationalId;
    private LocalDate registrationDate;
}