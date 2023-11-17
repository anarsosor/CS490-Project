package HealthCare.service;

import HealthCare.dto.AppointmentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "appointmentService")
public interface AppointmentFeignClient {
    @GetMapping("/appointments/{id}")
    ResponseEntity<AppointmentDTO> getAppointmentById(
            @PathVariable long id, @RequestHeader long userId, @RequestHeader boolean isDoctor,
            @RequestHeader boolean isPatient, @RequestHeader boolean isPharmacist);
}
