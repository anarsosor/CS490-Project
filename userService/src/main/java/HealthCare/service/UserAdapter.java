package HealthCare.service;

import HealthCare.entity.*;
import HealthCare.dto.*;

public class UserAdapter {
    public static User getUser(UserDTO userDTO) {
        if (userDTO == null) return null;
        return new User(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getUsername(), userDTO.getPhone(),
                new Address(userDTO.getStreet(), userDTO.getCity(), userDTO.getZip()),userDTO.getGender());
    }

    public static UserDTO getUserDTO(User user) {
        if (user == null) return null;
        UserDTO userDTO = new UserDTO(user.getUsername(), user.getPassword(),
                user.getFirstName(), user.getLastName(), user.getGender(),
                user.getPhone(), user.getAddress().getStreet(), user.getAddress().getCity(), user.getAddress().getZip(),
                false, 0.0, Speciality.NONE,
                false, "", false, "");
        userDTO.setId(user.getId());
        for(Role role : user.getRoles()) {
            if (role instanceof Doctor) {
                userDTO.setDoctor(true);
                userDTO.setHourRate(((Doctor) role).getHourRate());
                userDTO.setSpeciality(((Doctor) role).getSpeciality());
            } else if (role instanceof Patient) {
                userDTO.setPatient(true);
                userDTO.setInsuranceId(((Patient) role).getInsuranceId());
            } else if (role instanceof Pharmacist) {
                userDTO.setPharmacist(true);
                userDTO.setPharmacyName(((Pharmacist) role).getPharmacyName());
            }
        }
        return userDTO;
    }

    public static DoctorDTO getDoctorDTO(User user) {
        if (user == null) return null;
        DoctorDTO doctorDTO = new DoctorDTO(user.getId(), user.getUsername(),
                user.getFirstName(), user.getLastName(), user.getGender(),
                user.getPhone(), user.getAddress().getStreet(), user.getAddress().getCity(), user.getAddress().getZip(),
                0.0, Speciality.NONE);
        for(Role role : user.getRoles()){
            if (role instanceof Doctor) {
                doctorDTO.setSpeciality(((Doctor) role).getSpeciality());
                doctorDTO.setHourRate(((Doctor) role).getHourRate());
                break;
            }
        }
        return doctorDTO;
    }

    public static PatientDTO getPatientDTO(User user) {
        if (user == null) return null;
        PatientDTO patientDTO = new PatientDTO(user.getId(), user.getUsername(),
                user.getFirstName(), user.getLastName(), user.getGender(),
                user.getPhone(), "");
        for(Role role : user.getRoles()){
            if (role instanceof Patient) {
                patientDTO.setInsuranceId(((Patient) role).getInsuranceId());
                break;
            }
        }
        return patientDTO;
    }
    public static PharmacistDTO getPharmacistDTO(User user) {
        if (user == null) return null;
        PharmacistDTO pharmacistDTO = new PharmacistDTO(user.getId(), user.getPhone(),
                user.getAddress().getStreet(), user.getAddress().getCity(), user.getAddress().getZip(), "");
        for(Role role : user.getRoles()){
            if (role instanceof Pharmacist) {
                pharmacistDTO.setPharmacyName(((Pharmacist) role).getPharmacyName());
                break;
            }
        }
        return pharmacistDTO;
    }

}
