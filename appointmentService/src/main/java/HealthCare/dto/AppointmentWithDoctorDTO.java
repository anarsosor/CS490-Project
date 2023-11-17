package HealthCare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentWithDoctorDTO   {
    AppointmentDTO appointmentDTO;
    DoctorDTO doctorDTO;
}
