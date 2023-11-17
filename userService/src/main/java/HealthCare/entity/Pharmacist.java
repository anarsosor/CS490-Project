package HealthCare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("pharmacist")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pharmacist extends Role {
    private String pharmacyName;
}
