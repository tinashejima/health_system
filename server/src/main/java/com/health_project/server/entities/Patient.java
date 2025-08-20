package com.health_project.server.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "patient")
@Data
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "person_id", unique = true, columnDefinition = "VARCHAR(10)")
    private String personId = "P-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

    @Column(name = "patient_id", columnDefinition = "VARCHAR(36)")
    private String patientId = UUID.randomUUID().toString();


    @Column(name = "registration_date", columnDefinition = "DATE")
    private LocalDate registrationDate = LocalDate.now();

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name cannot exceed 50 characters")
    @Column(name = "first_name", columnDefinition = "VARCHAR(50)")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    @Column(name = "last_name", columnDefinition = "VARCHAR(50)")
    private String lastName;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "^(MALE|FEMALE|OTHER)$", message = "Gender must be MALE, FEMALE or OTHER")
    @Column(name = "gender", columnDefinition = "VARCHAR(10)")
    private String gender;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    @Column(name = "date_of_birth", columnDefinition = "DATE")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Address is required")
    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "national_id", columnDefinition = "VARCHAR(20)")
    private String nationalId;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{6,10}$", message = "Phone number must be 6-10 digits")
    @Column(name = "phone_number", columnDefinition = "VARCHAR(15)")
    private String phoneNumber;

    @Email(message = "Email should be valid")
    @Column(name = "email", columnDefinition = "VARCHAR(100)")
    private String email;
}