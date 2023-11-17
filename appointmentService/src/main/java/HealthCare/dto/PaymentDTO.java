package HealthCare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
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
}
