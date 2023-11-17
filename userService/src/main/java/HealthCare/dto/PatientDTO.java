package HealthCare.dto;

import HealthCare.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String phone;
    private String insuranceId;
}
