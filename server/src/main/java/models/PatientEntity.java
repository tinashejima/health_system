package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "patient")
public class PatientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "patient_id")
    private UUID patientId;

    @Column(name = "first_name", nullable = false)
    private String patientName;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "sex")
    private String sex;

    @Column(name = "age")
    private Integer age;

    @Column(name = "city")
    private String city;

    @Column(name = "email")
    private String email;

    @Column(name = "next_of_kin")
    private String nextOfKin;

    @Column(name = "mobile")
    private String mobile;

    @Column(unique = true)
    private String nationalId;

    @Column(name = "address")
    private String address;

}
