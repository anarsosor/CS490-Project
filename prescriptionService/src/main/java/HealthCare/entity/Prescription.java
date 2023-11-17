package HealthCare.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long appointmentId;
    private long doctorId;
    private long pharmacistId;
    private long patientId;
    private String patientFirstName;
    private String patientLastName;
    private LocalDate creationDate;
    private String drugList;
    private PrescriptionStatus status;
    private double netPayment;

    public Prescription(long appointmentId, long doctorId, long pharmacistId, long patientId, String patientFirstName, String patientLastName, String drugList) {
        this.appointmentId = appointmentId;
        this.doctorId = doctorId;
        this.pharmacistId = pharmacistId;
        this.patientId = patientId;
        this.patientFirstName = patientFirstName;
        this.patientLastName = patientLastName;
        this.creationDate = LocalDate.now();
        this.drugList = drugList;
        this.status = PrescriptionStatus.NEW;
    }

}
