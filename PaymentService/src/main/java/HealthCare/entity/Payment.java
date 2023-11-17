package HealthCare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long appointmentId;
    private long patientId;
    private LocalDate creationDate;
    private PaymentStatus status;
    private BigDecimal doctorCharge;
    private BigDecimal prescriptionCharge;
    private BigDecimal totalCharge;
    private BigDecimal insuranceDeduction;
    private BigDecimal netPayment;

    public Payment(long appointmentId, long patientId, BigDecimal doctorCharge, BigDecimal prescriptionCharge) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.creationDate = LocalDate.now();
        this.status = PaymentStatus.NEW;
        this.doctorCharge = doctorCharge;
        this.prescriptionCharge = prescriptionCharge;
        this.totalCharge = doctorCharge.add(prescriptionCharge);
        this.insuranceDeduction = this.totalCharge.multiply(new BigDecimal(0.7));
        this.netPayment = this.totalCharge.subtract(this.insuranceDeduction);
    }
}
