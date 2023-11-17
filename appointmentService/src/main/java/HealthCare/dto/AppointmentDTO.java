package HealthCare.dto;

import HealthCare.entity.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AppointmentDTO {
    private long id;
    private long patientId;
    private long doctorId;
    private LocalDateTime dateTime;
    private String meetingLink;
    private AppointmentStatus status;
    private String result;

    public AppointmentDTO(long patientId, long doctorId, LocalDateTime dateTime) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.dateTime = dateTime;
        this.status = AppointmentStatus.NEW;
    }
}
