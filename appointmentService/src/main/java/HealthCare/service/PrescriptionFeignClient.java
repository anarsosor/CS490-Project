package HealthCare.service;

import HealthCare.dto.PaymentDTO;
import HealthCare.dto.PrescriptionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "prescriptionService")
public interface PrescriptionFeignClient {
    @PostMapping("/prescriptions")
    ResponseEntity<PrescriptionDTO> addPrescription(@RequestBody PrescriptionDTO prescriptionDTO,
                                                    @RequestHeader long userId, @RequestHeader boolean isDoctor,
                                                    @RequestHeader boolean isPatient, @RequestHeader boolean isPharmacist);
}
