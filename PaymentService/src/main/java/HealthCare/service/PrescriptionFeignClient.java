package HealthCare.service;

import HealthCare.dto.PrescriptionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "prescriptionService")
public interface PrescriptionFeignClient {
    @PostMapping("/prescriptions")
    ResponseEntity<PrescriptionDTO> addPrescription(@RequestBody PrescriptionDTO prescriptionDTO,
                                                    @RequestHeader long userId, @RequestHeader boolean isDoctor,
                                                    @RequestHeader boolean isPatient, @RequestHeader boolean isPharmacist);
    @GetMapping("/prescriptions/appointment/{id}/pharmacist")
    ResponseEntity<List<PrescriptionDTO>> getPrescriptionsByAppointmentIdAndPharmacistId(
            @PathVariable long id, @RequestHeader long userId, @RequestHeader boolean isDoctor,
            @RequestHeader boolean isPatient, @RequestHeader boolean isPharmacist);
}
