package HealthCare.controller;

import HealthCare.dto.PrescriptionDTO;
import HealthCare.dto.PrescriptionDeliveryDTO;
import HealthCare.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PrescriptionController {
    @Autowired
    PrescriptionService prescriptionService;

    @GetMapping("/prescriptions/{id}")
    ResponseEntity<PrescriptionDTO> getPrescriptionById(@PathVariable long id, @RequestHeader long userId, @RequestHeader boolean isDoctor,
                                                        @RequestHeader boolean isPatient, @RequestHeader boolean isPharmacist) {
        return new ResponseEntity<PrescriptionDTO>(prescriptionService.getPrescriptionById(id, userId, isDoctor, isPatient, isPharmacist), HttpStatus.OK);
    }
    @GetMapping("/prescriptions")
    ResponseEntity<List<PrescriptionDTO>> getAllPrescriptions() {
        return new ResponseEntity<List<PrescriptionDTO>>(prescriptionService.getAllPrescriptions(), HttpStatus.OK);
    }
    @GetMapping("/prescriptions/pharmacist/{id}")
    ResponseEntity<List<PrescriptionDTO>> getPrescriptionsByPharmacistId(@PathVariable long id,
                                                                         @RequestHeader long userId, @RequestHeader boolean isDoctor,
                                                                         @RequestHeader boolean isPatient, @RequestHeader boolean isPharmacist) {
        if(!isPharmacist) throw new RuntimeException("Only Pharmacist can get the Prescription list. You are not authorized");
        return new ResponseEntity<List<PrescriptionDTO>>(prescriptionService.getPrescriptionsByPharmacistId(userId), HttpStatus.OK);
    }
    @GetMapping("/prescriptions/patient/{id}")
    ResponseEntity<List<PrescriptionDTO>> getPrescriptionsByPatientId(@PathVariable long id,
                                                                      @RequestHeader long userId, @RequestHeader boolean isDoctor,
                                                                      @RequestHeader boolean isPatient, @RequestHeader boolean isPharmacist) {
        if(!isPatient) throw new RuntimeException("Only Patient can get the Prescription list. You are not authorized");
        return new ResponseEntity<List<PrescriptionDTO>>(prescriptionService.getPrescriptionsByPatientId(userId), HttpStatus.OK);
    }
    @GetMapping("/prescriptions/doctor/{id}")
    ResponseEntity<List<PrescriptionDTO>> getPrescriptionsByDoctorId(@PathVariable long id,
                                                                     @RequestHeader long userId, @RequestHeader boolean isDoctor,
                                                                     @RequestHeader boolean isPatient, @RequestHeader boolean isPharmacist) {
        if(!isDoctor) throw new RuntimeException("Only Doctor can get the Prescription list. You are not authorized");
        return new ResponseEntity<List<PrescriptionDTO>>(prescriptionService.getPrescriptionsByDoctorId(userId), HttpStatus.OK);
    }
    @GetMapping("/prescriptions/appointment/{id}")
    ResponseEntity<List<PrescriptionDTO>> getPrescriptionsByAppointmentId(@PathVariable long id, @RequestHeader long userId, @RequestHeader boolean isDoctor,
                                                                          @RequestHeader boolean isPatient, @RequestHeader boolean isPharmacist) {
        return new ResponseEntity<List<PrescriptionDTO>>(prescriptionService.getPrescriptionsByAppointmentId(id, userId, isDoctor, isPatient, isPharmacist), HttpStatus.OK);
    }

    @GetMapping("/prescriptions/appointment/{id}/pharmacist")
    ResponseEntity<List<PrescriptionDTO>> getPrescriptionsByAppointmentIdAndPharmacistId(
            @PathVariable long id, @RequestHeader long userId, @RequestHeader boolean isDoctor,
            @RequestHeader boolean isPatient, @RequestHeader boolean isPharmacist) {
        return new ResponseEntity<List<PrescriptionDTO>>(prescriptionService.getPrescriptionsByAppointmentIdAndPharmacistId(id, userId, isDoctor, isPatient, isPharmacist), HttpStatus.OK);
    }
    @PostMapping("/prescriptions")
    ResponseEntity<PrescriptionDTO> addPrescription(@RequestBody PrescriptionDTO prescriptionDTO,
                                                    @RequestHeader long userId, @RequestHeader boolean isDoctor,
                                                    @RequestHeader boolean isPatient, @RequestHeader boolean isPharmacist) {
        if(!isDoctor) throw new RuntimeException("Only Doctor can add the Prescription. You are not authorized");
        prescriptionDTO.setDoctorId(userId);
        return new ResponseEntity<PrescriptionDTO>(prescriptionService.addPrescription(prescriptionDTO, userId, isDoctor, isPatient, isPharmacist), HttpStatus.OK);
    }

    @PostMapping("/prescriptions/delivery")
    public ResponseEntity<PrescriptionDTO> deliverPrescription(@RequestBody PrescriptionDeliveryDTO prescriptionDeliveryDTO,
                                                               @RequestHeader long userId, @RequestHeader boolean isDoctor,
                                                               @RequestHeader boolean isPatient, @RequestHeader boolean isPharmacist) {
        if(!isPharmacist) throw new RuntimeException("Only Pharmacist can deliver the Prescription. You are not authorized");
        return new ResponseEntity<PrescriptionDTO>(prescriptionService.deliverPrescription(prescriptionDeliveryDTO, userId), HttpStatus.OK);
    }

}
