package HealthCare.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Data
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long patientId;
    private long doctorId;
    private LocalDateTime dateTime;
    private String meetingLink;
    private AppointmentStatus status;
    private String result;

    public Appointment(long patientId, long doctorId, LocalDateTime dateTime) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.dateTime = dateTime;
        this.meetingLink = "https://meeting.com/meeting"+patientId+doctorId+dateTime;
        this.status = AppointmentStatus.NEW;
        this.result = "";
    }
}
