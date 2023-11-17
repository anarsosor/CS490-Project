package HealthCare.service;

import HealthCare.dto.DoctorDTO;
import HealthCare.dto.PatientDTO;
import HealthCare.dto.PharmacistDTO;
import HealthCare.dto.Speciality;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "userService")
public interface UserFeignClient {
    @GetMapping("/doctors/speciality/{speciality}")
    ResponseEntity<List<DoctorDTO>> getDoctorsBySpeciality(@PathVariable Speciality speciality);
    @GetMapping("/doctors/{id}")
    ResponseEntity<DoctorDTO> getDoctorById(@PathVariable long id);

    @GetMapping("/doctors")
    ResponseEntity<List<DoctorDTO>> getAllDoctors();

    @GetMapping("/patients/{id}")
    ResponseEntity<PatientDTO> getPatientById(@PathVariable long id);

    @GetMapping("/patients")
    ResponseEntity<List<PatientDTO>> getAllPatients();

    @GetMapping("/pharmacists/{id}")
    ResponseEntity<PharmacistDTO> getPharmacistById(@PathVariable long id);

    @GetMapping("/pharmacists")
    ResponseEntity<List<PharmacistDTO>> getAllPharmacist();

}
