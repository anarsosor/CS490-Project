package HealthCare.service;

import HealthCare.dto.PaymentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "paymentService")
public interface PaymentFeignClient {
    @PostMapping("/payments")
    ResponseEntity<PaymentDTO> addPayment(
            @RequestBody PaymentDTO paymentDTO, @RequestHeader long userId, @RequestHeader boolean isDoctor,
            @RequestHeader boolean isPatient, @RequestHeader boolean isPharmacist);
}
