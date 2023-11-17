package HealthCare.repository;

import HealthCare.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findByPatientIdAndDoctorIdAndDateTime(long patientId, long doctorId, LocalDateTime dateTime);
    List<Appointment> findByPatientId(long patientId);
    @Query("select a from Appointment a where a.doctorId = :doctorId order by a.dateTime DESC")
    List<Appointment> findByDoctorId(long doctorId);

    @Query("select a.dateTime from Appointment a where a.doctorId = :doctorId and FUNCTION('DATE', a.dateTime) = FUNCTION('DATE', :dateTime)")
    List<LocalDateTime> findByDoctorIdAndDate(long doctorId, LocalDateTime dateTime);

    @Query("select a from Appointment a where a.doctorId = :doctorId and a.dateTime = :dateTime")
    List<Appointment> findByDoctorIdAndDateTime(long doctorId, LocalDateTime dateTime);

}
