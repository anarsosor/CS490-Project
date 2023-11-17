package HealthCare.repository;

import HealthCare.entity.Speciality;
import HealthCare.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    public Optional<User> findByUsername(String username);

    @Query("select u from Doctor d join d.user u where u.id = :id")
    public Optional<User> findDoctorById(long id);

    @Query("select u from Doctor d join d.user u")
    public List<User> findAllDoctors();

    @Query("select u from Patient p join p.user u where u.id = :id")
    public Optional<User> findPatientById(long id);

    @Query("select u from Patient p join p.user u")
    public List<User> findAllPatients();

    @Query("select u from Pharmacist p join p.user u where u.id = :id")
    public Optional<User> findPharmacistById(long id);

    @Query("select u from Pharmacist p join p.user u")
    public List<User> findAllPharmacists();

    @Query("select u from Doctor d join d.user u where d.speciality = :speciality")
    public List<User> findDoctorsBySpeciality(Speciality speciality);
}
