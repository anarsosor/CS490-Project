package HealthCare.service;

import HealthCare.dto.PaymentDTO;
import HealthCare.entity.Payment;

public class PaymentAdapter {

    public static PaymentDTO getPaymentDTO(Payment payment) {
        return new PaymentDTO(payment.getId(), payment.getAppointmentId(), payment.getPatientId(), payment.getCreationDate(),
                payment.getStatus(), payment.getDoctorCharge(), payment.getPrescriptionCharge(),
                payment.getTotalCharge(), payment.getInsuranceDeduction(), payment.getNetPayment());
    }

    public static Payment getPayment(PaymentDTO paymentDTO) {
        return new Payment(paymentDTO.getAppointmentId(), paymentDTO.getPatientId(),
                paymentDTO.getDoctorCharge(), paymentDTO.getPrescriptionCharge());
    }


}
