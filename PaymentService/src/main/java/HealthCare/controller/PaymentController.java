package HealthCare.controller;

import HealthCare.dto.PaymentDTO;
import HealthCare.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @PostMapping("/payments")
    ResponseEntity<PaymentDTO> addPayment(
            @RequestBody PaymentDTO paymentDTO, @RequestHeader long userId, @RequestHeader boolean isDoctor,
            @RequestHeader boolean isPatient, @RequestHeader boolean isPharmacist) {
        return new ResponseEntity<PaymentDTO>(paymentService.addPayment(paymentDTO, userId, isDoctor, isPatient, isPharmacist), HttpStatus.OK);
    }

    @PostMapping("/payments/{id}")
    ResponseEntity<PaymentDTO> makePayment(@PathVariable long id,
                                           @RequestHeader long userId, @RequestHeader boolean isPatient) {
        if(!isPatient) throw new RuntimeException("Only Patient can make the Payment. You are not authorized");
        return new ResponseEntity<PaymentDTO>(paymentService.makePayment(id, userId, isPatient), HttpStatus.OK);
    }
    @GetMapping("/payments")
    ResponseEntity<List<PaymentDTO>> getAllPayments() {
        return new ResponseEntity<List<PaymentDTO>>(paymentService.getAllPayments(), HttpStatus.OK);
    }
    @GetMapping("/payments/{id}")
    ResponseEntity<PaymentDTO> getPaymentById(
            @PathVariable long id, @RequestHeader long userId, @RequestHeader boolean isDoctor,
            @RequestHeader boolean isPatient, @RequestHeader boolean isPharmacist) {
        return new ResponseEntity<PaymentDTO>(paymentService.getPaymentById(id, userId, isDoctor, isPatient, isPharmacist), HttpStatus.OK);
    }
    @GetMapping("/payments/patient/{id}")
    ResponseEntity<List<PaymentDTO>> getPaymentsByPatientId(
            @PathVariable long id, @RequestHeader long userId, @RequestHeader boolean isPatient) {
        if(!isPatient) throw new RuntimeException("Only Patient can get the Payment list. You are not authorized");
        return new ResponseEntity<List<PaymentDTO>>(paymentService.getPaymentsByPatientId(userId), HttpStatus.OK);
    }

}
