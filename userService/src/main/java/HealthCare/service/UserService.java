package HealthCare.service;

import HealthCare.entity.*;
import HealthCare.dto.*;
import HealthCare.repository.DoctorRepo;
import HealthCare.repository.PatientRepo;
import HealthCare.repository.PharmacistRepo;
import HealthCare.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    DoctorRepo doctorRepo;
    @Autowired
    PatientRepo patientRepo;
    @Autowired
    PharmacistRepo pharmacistRepo;

    public UserDTO addUser(UserDTO userDTO) {
        if(!userRepo.findByUsername(userDTO.getUsername()).isEmpty()){
            throw new RuntimeException("The user has already registered with name: " + userDTO.getUsername());
        }
        User user = UserAdapter.getUser(userDTO);
        String salt = BCrypt.gensalt(12);
        String hashedPassword = BCrypt.hashpw(userDTO.getPassword(), salt);
        user.setPassword(hashedPassword);

        if(userDTO.isDoctor()) {
            Doctor doctor = new Doctor(userDTO.getHourRate(), userDTO.getSpeciality());
            doctor.setUser(user);
            user.getRoles().add(doctor);
            doctorRepo.save(doctor);
        }
        if(userDTO.isPatient()) {
            Patient patient = new Patient(userDTO.getInsuranceId());
            patient.setUser(user);
            user.getRoles().add(patient);
            patientRepo.save(patient);
        }
        if(userDTO.isPharmacist()) {
            Pharmacist pharmacist = new Pharmacist(userDTO.getPharmacyName());
            pharmacist.setUser(user);
            user.getRoles().add(pharmacist);
            pharmacistRepo.save(pharmacist);
        }
        return UserAdapter.getUserDTO(userRepo.save(user));
    }

    public UserDTO getUserByUsername(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        return UserAdapter.getUserDTO(user);
    }
    public UserDTO getUserById(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return UserAdapter.getUserDTO(user);
    }
    public UserDTO checkCredential(AuthDTO authDTO) throws RuntimeException {
        User user = userRepo.findByUsername(authDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found with username : " + authDTO.getUsername()));
        if(!BCrypt.checkpw(authDTO.getPassword(), user.getPassword()))
            throw new RuntimeException("Invalid username or password");
        return UserAdapter.getUserDTO(user);
    }
    public List<UserDTO> getAllUsers() {
        List<User> userList = userRepo.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        userList.forEach(u -> userDTOList.add(UserAdapter.getUserDTO(u)));
        return userDTOList;
    }
    public void deleteUserById(long id){
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        for (Role role : user.getRoles()) {
            if(role instanceof Doctor) doctorRepo.delete((Doctor) role);
            else if (role instanceof Patient) patientRepo.delete((Patient) role);
            else if (role instanceof Pharmacist) pharmacistRepo.delete((Pharmacist) role);
        }
        userRepo.delete(user);
    }

    public DoctorDTO getDoctorById(long id) {
        User user = userRepo.findDoctorById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
        return UserAdapter.getDoctorDTO(user);
    }

    public PatientDTO getPatientById(long id) {
        User user = userRepo.findPatientById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
        return UserAdapter.getPatientDTO(user);
    }

    public PharmacistDTO getPharmacistById(long id) {
        User user = userRepo.findPharmacistById(id)
                .orElseThrow(() -> new RuntimeException("Pharmacist not found with id: " + id));
        return UserAdapter.getPharmacistDTO(user);
    }

    public List<DoctorDTO> getAllDoctors() {
        List<DoctorDTO> doctorDTOList = new ArrayList<>();
        List<User> doctorList = userRepo.findAllDoctors();
        doctorList.forEach(d -> doctorDTOList.add(UserAdapter.getDoctorDTO(d)));
        return doctorDTOList;
    }
    public List<PatientDTO> getAllPatients() {
        List<PatientDTO> patientDTOList = new ArrayList<>();
        List<User> doctorList = userRepo.findAllPatients();
        doctorList.forEach(d -> patientDTOList.add(UserAdapter.getPatientDTO(d)));
        return patientDTOList;
    }
    public List<PharmacistDTO> getAllPharmacists() {
        List<PharmacistDTO> pharmacistDTOList = new ArrayList<>();
        List<User> doctorList = userRepo.findAllPharmacists();
        doctorList.forEach(d -> pharmacistDTOList.add(UserAdapter.getPharmacistDTO(d)));
        return pharmacistDTOList;
    }

    public List<DoctorDTO> getDoctorsBySpeciality(Speciality speciality) {
        List<DoctorDTO> doctorDTOList = new ArrayList<>();
        List<User> userList = userRepo.findDoctorsBySpeciality(speciality);
        userList.forEach(u -> doctorDTOList.add(UserAdapter.getDoctorDTO(u)));
        return doctorDTOList;
    }
}
