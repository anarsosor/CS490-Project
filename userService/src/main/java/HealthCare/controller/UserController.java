package HealthCare.controller;

import HealthCare.entity.Speciality;
import HealthCare.dto.*;
import HealthCare.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/users")
    ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<List<UserDTO>>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    ResponseEntity<UserDTO> getUser(@PathVariable long id) {
        return new ResponseEntity<UserDTO>(userService.getUserById(id), HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    ResponseEntity<?> deleteUser(@PathVariable long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/doctors/speciality/{speciality}")
    ResponseEntity<List<DoctorDTO>> getDoctorsBySpeciality(@PathVariable Speciality speciality) {
        return new ResponseEntity<List<DoctorDTO>>(userService.getDoctorsBySpeciality(speciality), HttpStatus.OK);
    }
    @GetMapping("/doctors/{id}")
    ResponseEntity<DoctorDTO> getDoctorById(@PathVariable long id) {
        return new ResponseEntity<DoctorDTO>(userService.getDoctorById(id), HttpStatus.OK);
    }

    @GetMapping("/doctors")
    ResponseEntity<List<DoctorDTO>> getAllDoctors() {
        return new ResponseEntity<List<DoctorDTO>>(userService.getAllDoctors(), HttpStatus.OK);
    }

    @GetMapping("/patients/{id}")
    ResponseEntity<PatientDTO> getPatientById(@PathVariable long id) {
        return new ResponseEntity<PatientDTO>(userService.getPatientById(id), HttpStatus.OK);
    }

    @GetMapping("/patients")
    ResponseEntity<List<PatientDTO>> getAllPatients() {
        return new ResponseEntity<List<PatientDTO>>(userService.getAllPatients(), HttpStatus.OK);
    }

    @GetMapping("/pharmacists/{id}")
    ResponseEntity<PharmacistDTO> getPharmacistById(@PathVariable long id) {
        return new ResponseEntity<PharmacistDTO>(userService.getPharmacistById(id), HttpStatus.OK);
    }

    @GetMapping("/pharmacists")
    ResponseEntity<List<PharmacistDTO>> getAllPharmacist() {
        return new ResponseEntity<List<PharmacistDTO>>(userService.getAllPharmacists(), HttpStatus.OK);
    }

}
