package HealthCare.repository;

import HealthCare.entity.Prescription;
import HealthCare.entity.PrescriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PrescriptionRepo extends JpaRepository<Prescription, Long> {
    public List<Prescription> findByAppointmentId(long appointmentId);
    public List<Prescription> findByDoctorId(long doctorId);
    public List<Prescription> findByPharmacistId(long pharmacistId);
    public List<Prescription> findByPatientId(long patientId);
    public List<Prescription> findByAppointmentIdAndPharmacistId(long appointmentId, long pharmacistId);
    public List<Prescription> findByAppointmentIdAndStatus(long appointmentId, PrescriptionStatus status);
    @Query("select sum(p.netPayment) from Prescription p where p.appointmentId = :appointmentId")
    public BigDecimal calculateSumOfNetPaymentByAppointmentId(long appointmentId);
}
