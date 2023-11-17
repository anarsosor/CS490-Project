package HealthCare.dto;

import HealthCare.entity.Speciality;
import HealthCare.entity.Gender;
import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class UserDTO {
    private long id;
    @NonNull private String username;
    @NonNull private String password;
    @NonNull private String firstName;
    @NonNull private String lastName;
    @NonNull private Gender gender;
    @NonNull private String phone;
    @NonNull private String street;
    @NonNull private String city;
    @NonNull private String zip;
    @NonNull private boolean doctor;
    @NonNull private double hourRate;
    @NonNull private Speciality speciality;
    @NonNull private boolean patient;
    @NonNull private String insuranceId;
    @NonNull private boolean pharmacist;
    @NonNull private String pharmacyName;
}
