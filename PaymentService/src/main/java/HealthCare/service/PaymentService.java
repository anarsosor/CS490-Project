package HealthCare.service;

import HealthCare.dto.AppointmentDTO;
import HealthCare.dto.PaymentDTO;
import HealthCare.dto.PrescriptionDTO;
import HealthCare.entity.Payment;
import HealthCare.entity.PaymentStatus;
import HealthCare.repository.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PaymentService {
    @Autowired
    PaymentRepo paymentRepo;

    @Autowired
    AppointmentFeignClient appointmentFeignClient;

    @Autowired
    PrescriptionFeignClient prescriptionFeignClient;

    public PaymentDTO addPayment(PaymentDTO paymentDTO, long userId, boolean isDoctor, boolean isPatient, boolean isPharmacist) {
        AppointmentDTO appointmentDTO = appointmentFeignClient.getAppointmentById(paymentDTO.getAppointmentId(), userId, isDoctor, isPatient, isPharmacist).getBody();
        if (isDoctor && appointmentDTO.getDoctorId() == userId) {
            Payment payment = PaymentAdapter.getPayment(paymentDTO);
            return PaymentAdapter.getPaymentDTO(paymentRepo.save(payment));
        } else if(isPharmacist) {
            List<PrescriptionDTO> prescriptionDTOList = prescriptionFeignClient
                    .getPrescriptionsByAppointmentIdAndPharmacistId(paymentDTO.getAppointmentId(), userId, isDoctor, isPatient, true).getBody();
            if (!prescriptionDTOList.isEmpty()) {
                Payment payment = PaymentAdapter.getPayment(paymentDTO);
                return PaymentAdapter.getPaymentDTO(paymentRepo.save(payment));
            }
        }
        throw new RuntimeException("Yoy have no permission to add this payment");
    }

    public PaymentDTO makePayment(long id, long userId, boolean isPatient) {
        Payment payment = paymentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + id));
        AppointmentDTO appointmentDTO = appointmentFeignClient.getAppointmentById(payment.getAppointmentId(), userId, false, isPatient, false).getBody();
        if (payment.getStatus() == PaymentStatus.BILLED)
            throw new RuntimeException("This payment is already billed");
        if (appointmentDTO.getPatientId() != userId)
            throw new RuntimeException("This payment is addressed to another Patient. Check the payment Id");
        payment.setStatus(PaymentStatus.BILLED);
        paymentRepo.save(payment);
        return PaymentAdapter.getPaymentDTO(payment);
    }

    public List<PaymentDTO> getAllPayments() {
        List<Payment> paymentList = paymentRepo.findAll();
        List<PaymentDTO> paymentDTOList = new ArrayList<>();
        paymentList.forEach(p -> paymentDTOList.add(PaymentAdapter.getPaymentDTO(p)));
        return paymentDTOList;
    }

    public PaymentDTO getPaymentById(long id, long userId, boolean isDoctor, boolean isPatient, boolean isPharmacist) {
        Payment payment = paymentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("The Payment not found with ID: " + id));
        AppointmentDTO appointmentDTO = appointmentFeignClient.getAppointmentById(payment.getAppointmentId(), userId, isDoctor, isPatient, isPharmacist).getBody();
        if (appointmentDTO.getDoctorId() == userId || appointmentDTO.getPatientId() == userId)
            return PaymentAdapter.getPaymentDTO(payment);
        else
            throw new RuntimeException("You are not authorized to get this payment");
    }

    public List<PaymentDTO> getPaymentsByPatientId(long patientId) {
        List<Payment> paymentList = paymentRepo.findByPatientId(patientId);
        List<PaymentDTO> paymentDTOList = new ArrayList<>();
        paymentList.forEach(p -> paymentDTOList.add(PaymentAdapter.getPaymentDTO(p)));
        return paymentDTOList;
    }

}
