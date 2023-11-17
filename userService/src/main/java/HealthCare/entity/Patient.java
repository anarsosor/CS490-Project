package HealthCare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("patient")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient extends Role {
    private String insuranceId;
}
