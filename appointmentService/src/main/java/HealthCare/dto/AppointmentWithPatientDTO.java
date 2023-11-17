package HealthCare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentWithPatientDTO {
    AppointmentDTO appointmentDTO;
    PatientDTO patientDTO;
}
