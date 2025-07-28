package repositories;

import entities.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
    boolean existsByNationalId(String nationalId);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
}
