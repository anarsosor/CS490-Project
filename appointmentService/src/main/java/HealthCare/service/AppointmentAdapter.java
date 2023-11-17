package HealthCare.service;

import HealthCare.dto.AppointmentDTO;
import HealthCare.entity.Appointment;

public class AppointmentAdapter {
    public static Appointment getAppointment(AppointmentDTO appointmentDTO) {
        return new Appointment(appointmentDTO.getPatientId(), appointmentDTO.getDoctorId(),
                appointmentDTO.getDateTime());
    }

    public static AppointmentDTO getAppointmentDTO(Appointment appointment) {
        AppointmentDTO appointmentDTO = new AppointmentDTO(appointment.getPatientId(), appointment.getDoctorId(),
                appointment.getDateTime());
        appointmentDTO.setId(appointment.getId());
        appointmentDTO.setResult(appointment.getResult());
        appointmentDTO.setStatus(appointment.getStatus());
        appointmentDTO.setMeetingLink(appointment.getMeetingLink());
        return appointmentDTO;
    }

}
