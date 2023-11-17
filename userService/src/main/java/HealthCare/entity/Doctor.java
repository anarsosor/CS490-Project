package HealthCare.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("doctor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor extends Role {
    private double hourRate;
    @Enumerated(EnumType.STRING)
    private Speciality speciality;
}