package HealthCare.repository;

import HealthCare.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {
    List<Payment> findByAppointmentId(long appointmentID);
    List<Payment> findByPatientId(long patientID);
}
