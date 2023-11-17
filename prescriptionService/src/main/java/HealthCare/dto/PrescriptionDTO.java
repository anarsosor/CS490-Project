package HealthCare.dto;

import HealthCare.entity.PrescriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionDTO {
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
}
