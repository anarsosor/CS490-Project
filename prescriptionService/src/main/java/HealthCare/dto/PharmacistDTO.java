package HealthCare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PharmacistDTO {
    private long id;
    private String phone;
    private String street;
    private String city;
    private String zip;
    private String pharmacyName;
}
