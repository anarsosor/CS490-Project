package HealthCare.controller;

import HealthCare.dto.*;
import HealthCare.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class AppointmentController {
    @Autowired
    AppointmentService appointmentService;

    @GetMapping("/appointments")
    ResponseEntity<List<AppointmentDTO>> getAllAppointments(){
        return new ResponseEntity<List<AppointmentDTO>>(appointmentService.getAllAppointments(), HttpStatus.OK);
    }

    @GetMapping("/appointments/patient/{patientId}")
    ResponseEntity<List<AppointmentWithDoctorDTO>> getAppointmentsWithDoctorByPatient(@PathVariable long patientId,
                                          @RequestHeader long userId, @RequestHeader boolean isPatient){
        if(!isPatient) throw new RuntimeException("Only Patient can view the appointments in detail. You are not authorized");
        return new ResponseEntity<List<AppointmentWithDoctorDTO>>(appointmentService.getAppointmentsWithDoctorByPatient(userId), HttpStatus.OK);
    }

    @GetMapping("/appointments/doctor")
    ResponseEntity<List<AppointmentWithPatientDTO>> getAppointmentsWithPatientByDoctor(@RequestHeader long userId, @RequestHeader boolean isDoctor){
        if(!isDoctor) throw new RuntimeException("Only Doctor can view the appointments in detail. You are not authorized");
        return new ResponseEntity<List<AppointmentWithPatientDTO>>(appointmentService.getAppointmentsWithPatientByDoctor(userId), HttpStatus.OK);
    }

    @PostMapping("/appointments/doctor/date")
    ResponseEntity<List<String>> getAppointmentsByDoctorAndDate(@RequestBody AppointmentDTO appointmentDTO){
        return new ResponseEntity<List<String>>(appointmentService.getAppointmentsByDoctorAndDate(appointmentDTO), HttpStatus.OK);
    }

    @GetMapping("/appointments/{id}")
    ResponseEntity<AppointmentDTO> getAppointmentById(
            @PathVariable long id, @RequestHeader long userId, @RequestHeader boolean isDoctor,
            @RequestHeader boolean isPatient, @RequestHeader boolean isPharmacist){
        AppointmentDTO appointmentDTO = appointmentService.getAppointmentById(id);
        if (isPharmacist || (isDoctor && appointmentDTO.getDoctorId() == userId) || isPatient && appointmentDTO.getPatientId() == userId)
            return new ResponseEntity<AppointmentDTO>(appointmentDTO, HttpStatus.OK);
        else
            throw new RuntimeException("You are not authorized to get this appointment");
    }
//    @GetMapping("/appointments/prescription/{id}")
//    ResponseEntity<AppointmentDTO> getAppointmentByPrescriptionId(@PathVariable long id){
//        return new ResponseEntity<AppointmentDTO>(appointmentService.getAppointmentByPrescriptionId(id), HttpStatus.OK);
//    }

    @PostMapping("/appointments")
    ResponseEntity<AppointmentDTO> makeAppointment(@RequestBody AppointmentDTO appointmentDTO,
                                                   @RequestHeader long userId, @RequestHeader boolean isPatient) {
        if(!isPatient) throw new RuntimeException("Only Patient can make appointment. You are not authorized");
        appointmentDTO.setPatientId(userId);
        return new ResponseEntity<AppointmentDTO>(appointmentService.makeAppointment(appointmentDTO), HttpStatus.CREATED);
    }

    @PutMapping("/appointments/{id}")
    ResponseEntity<AppointmentDTO> modifyAppointment(@PathVariable long id, @RequestBody AppointmentDTO appointmentDTO,
                                                     @RequestHeader long userId, @RequestHeader boolean isPatient) {
        if (id != appointmentDTO.getId()) throw new RuntimeException("Appointment id did not match");
        if(!isPatient) throw new RuntimeException("Only Patient can modify the appointment. You are not authorized");
        return new ResponseEntity<AppointmentDTO>(appointmentService.modifyAppointment(id, appointmentDTO, userId), HttpStatus.OK);
    }

    @DeleteMapping("/appointments/{id}")
    ResponseEntity<?> deleteAppointment(@PathVariable long id,
                                        @RequestHeader long userId, @RequestHeader boolean isPatient) {
        if(!isPatient) throw new RuntimeException("Only Patient can cancel the appointment. You are not authorized");
        appointmentService.deleteAppointment(id, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/appointments/checkup")
    ResponseEntity<AppointmentDTO> chekUpAppointment(@RequestBody CheckupDTO checkupDTO,
                                                     @RequestHeader long userId, @RequestHeader boolean isDoctor) {
        if(!isDoctor) throw new RuntimeException("Only Doctor can checkup the appointment. You are not authorized");
        return new ResponseEntity<AppointmentDTO>(appointmentService.checkUpAppointment(checkupDTO, userId), HttpStatus.OK);
    }


}
