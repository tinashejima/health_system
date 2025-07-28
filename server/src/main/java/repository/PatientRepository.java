package repository;

import models.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PatientRepo {


    @Repository
    public interface PatientRepository extends JpaRepository<PatientEntity.Patient, Long> {

        /**
         * Find patient by email address
         * @param email the email to search for
         * @return Optional containing patient if found
         */
        Optional<PatientEntity.Patient> findByEmail(String email);

        /**
         * Find patient by phone number
         * @param phoneNumber the phone number to search for
         * @return Optional containing patient if found
         */
        Optional<PatientEntity.Patient> findByPhoneNumber(String phoneNumber);

        /**
         * Check if patient exists by email
         * @param email the email to check
         * @return true if patient exists with this email
         */
        boolean existsByEmail(String email);

        /**
         * Check if patient exists by phone number
         * @param phoneNumber the phone number to check
         * @return true if patient exists with this phone number
         */
        boolean existsByPhoneNumber(String phoneNumber);

        /**
         * Find patients by first name and last name
         * @param firstName the first name to search for
         * @param lastName the last name to search for
         * @return List of patients matching the criteria
         */
        List<PatientEntity.Patient> findByFirstNameAndLastName(String firstName, String lastName);

        /**
         * Find patients by date of birth
         * @param dateOfBirth the date of birth to search for
         * @return List of patients with matching date of birth
         */
        List<PatientEntity.Patient> findByDateOfBirth(LocalDate dateOfBirth);

        /**
         * Find patients by first name containing (case insensitive)
         * @param firstName the first name pattern to search for
         * @return List of patients whose first name contains the pattern
         */
        List<PatientEntity.Patient> findByFirstNameContainingIgnoreCase(String firstName);

        /**
         * Find patients by last name containing (case insensitive)
         * @param lastName the last name pattern to search for
         * @return List of patients whose last name contains the pattern
         */
        List<PatientEntity.Patient> findByLastNameContainingIgnoreCase(String lastName);

        /**
         * Custom query to find patients by full name search
         * @param searchTerm the search term for name
         * @return List of patients matching the search criteria
         */
        @Query("SELECT p FROM Patient p WHERE " +
                "LOWER(CONCAT(p.firstName, ' ', p.lastName)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
                "LOWER(CONCAT(p.lastName, ' ', p.firstName)) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
        List<PatientEntity.Patient> findByFullNameContaining(@Param("searchTerm") String searchTerm);

        /**
         * Find patients registered between two dates
         * @param startDate the start date
         * @param endDate the end date
         * @return List of patients registered in the date range
         */
        @Query("SELECT p FROM Patient p WHERE p.createdAt BETWEEN :startDate AND :endDate")
        List<PatientEntity.Patient> findPatientsRegisteredBetween(@Param("startDate") LocalDate startDate,
                                                                  @Param("endDate") LocalDate endDate);
}


