package HealthCare.service;

import HealthCare.dto.*;
import HealthCare.entity.*;
import HealthCare.repository.AppointmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Service
@Transactional
public class AppointmentService {
    @Autowired
    UserFeignClient userFeignClient;
    @Autowired
    PaymentFeignClient paymentFeignClient;
    @Autowired
    PrescriptionFeignClient prescriptionFeignClient;
    @Autowired
    AppointmentRepo appointmentRepo;


    public List<AppointmentDTO> getAllAppointments(){
        List<Appointment> appointmentList = appointmentRepo.findAll();
        List<AppointmentDTO> appointmentDTOList = new ArrayList<>();
        appointmentList.forEach(app -> appointmentDTOList.add(AppointmentAdapter.getAppointmentDTO(app)));
        return appointmentDTOList;
    }

    public List<AppointmentDTO> getAppointmentsByPatient(long patientId){
        List<Appointment> appointmentList = appointmentRepo.findByPatientId(patientId);
        List<AppointmentDTO> appointmentDTOList = new ArrayList<>();
        appointmentList.forEach(app -> appointmentDTOList.add(AppointmentAdapter.getAppointmentDTO(app)));
        return appointmentDTOList;
    }

    public List<AppointmentDTO> getAppointmentsByDoctor(long doctorId){
        List<Appointment> appointmentList = appointmentRepo.findByDoctorId(doctorId);
        List<AppointmentDTO> appointmentDTOList = new ArrayList<>();
        appointmentList.forEach(app -> appointmentDTOList.add(AppointmentAdapter.getAppointmentDTO(app)));
        return appointmentDTOList;
    }

    public List<String> getAppointmentsByDoctorAndDate(AppointmentDTO appointmentDTO) {
        long doctorId = appointmentDTO.getDoctorId();
        LocalDateTime dateTime = appointmentDTO.getDateTime();
        List<LocalDateTime> appointmentList = appointmentRepo.findByDoctorIdAndDate(doctorId, dateTime);
        List<String> hours = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h a");
        appointmentList.forEach(dt -> hours.add(dt.format(formatter)));
        return hours;
    }

    public AppointmentDTO getAppointmentById(long id) {
        Appointment appointment = appointmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("The Appointment not found with ID: " + id));
        return AppointmentAdapter.getAppointmentDTO(appointment);
    }
//    public AppointmentDTO getAppointmentByPrescriptionId(long id) {
//        Appointment appointment = appointmentRepo.findByPrescriptionId(id)
//                .orElseThrow(() -> new RuntimeException("The Appointment not found related with this prescription ID: " + id));
//        return AppointmentAdapter.getAppointmentDTO(appointment);
//    }

    public List<AppointmentWithDoctorDTO> getAppointmentsWithDoctorByPatient(long id) {
        List<AppointmentWithDoctorDTO> appWithDoctor = new ArrayList<>();
        List<AppointmentDTO> appointmentDTOList = getAppointmentsByPatient(id);
        appointmentDTOList.forEach( ap ->
                appWithDoctor.add(new AppointmentWithDoctorDTO(ap, userFeignClient.getDoctorById(ap.getDoctorId()).getBody()))
        );
        return  appWithDoctor;
    }

    public List<AppointmentWithPatientDTO> getAppointmentsWithPatientByDoctor(long id) {
        List<AppointmentWithPatientDTO> appWithPatient = new ArrayList<>();
        List<AppointmentDTO> appointmentDTOList = getAppointmentsByDoctor(id);
        appointmentDTOList.forEach( ap ->
                appWithPatient.add(new AppointmentWithPatientDTO(ap, userFeignClient.getPatientById(ap.getPatientId()).getBody()))
        );
        return  appWithPatient;
    }

    public AppointmentDTO makeAppointment(AppointmentDTO appointmentDTO) {
        if (appointmentDTO.getDoctorId() == appointmentDTO.getPatientId())
            throw new RuntimeException("You cannot schedule an appointment with yourself");
        userFeignClient.getDoctorById(appointmentDTO.getDoctorId());    // check existence of doctor
        List<Appointment> existingAppointment = appointmentRepo.findByDoctorIdAndDateTime(appointmentDTO.getDoctorId(), appointmentDTO.getDateTime());
        if (existingAppointment.size() != 0) {
            throw new RuntimeException("Another appointment is already scheduled with this doctor for this date and time");
        }
        Appointment appointment = AppointmentAdapter.getAppointment(appointmentDTO);
        return AppointmentAdapter.getAppointmentDTO(appointmentRepo.save(appointment));
    }

    public AppointmentDTO modifyAppointment(long id, AppointmentDTO appointmentDTO, long userId) {
        Appointment appointment = appointmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("The Appointment not found with ID: " + id));
        if (appointment.getPatientId() != userId) throw new RuntimeException("You are not authorized to modify this appointment");
        appointment.setDateTime(appointmentDTO.getDateTime());
        appointment.setDoctorId(appointmentDTO.getDoctorId());
        appointment.setMeetingLink(appointmentDTO.getMeetingLink());
        return AppointmentAdapter.getAppointmentDTO(appointmentRepo.save(appointment));
    }

    public void deleteAppointment(long id, long userId) {
        Appointment appointment = appointmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("The Appointment not found with ID: " + id));
        if(appointment.getPatientId() != userId)
            throw new RuntimeException("You are not authorized to modify this appointment");
        if(appointment.getStatus() != AppointmentStatus.NEW)
            throw new RuntimeException("The Appointment already expired or checked");
        appointmentRepo.deleteById(id);
    }

    public AppointmentDTO checkUpAppointment(CheckupDTO checkupDTO, long userId){
        Appointment appointment = appointmentRepo.findById(checkupDTO.getId())
                .orElseThrow(() -> new RuntimeException("The Appointment not found with ID: " + checkupDTO.getId()));
        if(appointment.getDoctorId() != userId)
            throw new RuntimeException("You are not authorized to checkup this appointment");
        if(appointment.getStatus() != AppointmentStatus.NEW)
            throw new RuntimeException("The Appointment already expired or checked");
        appointment.setStatus(AppointmentStatus.CHECKED);
        appointment.setResult(checkupDTO.getResult());
        appointment = appointmentRepo.save(appointment);

        List<PrescriptionDTO> prescriptionDTOList = checkupDTO.getPrescriptions();
        PatientDTO patientDTO = userFeignClient.getPatientById(appointment.getPatientId()).getBody();
        if (prescriptionDTOList.isEmpty()) {
            DoctorDTO doctorDTO = userFeignClient.getDoctorById(appointment.getDoctorId()).getBody();
            BigDecimal doctorCharge = new BigDecimal(doctorDTO.getHourRate());
            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setAppointmentId(appointment.getId());
            paymentDTO.setPatientId(appointment.getPatientId());
            paymentDTO.setDoctorCharge(doctorCharge);
            paymentDTO.setPrescriptionCharge(new BigDecimal(0));
            paymentFeignClient.addPayment(paymentDTO, userId, true, false, false);
        } else {
            Iterator<PrescriptionDTO> prescriptionDTOIterator = prescriptionDTOList.iterator();
            while(prescriptionDTOIterator.hasNext()) {
                PrescriptionDTO prescriptionDTO = prescriptionDTOIterator.next();
                prescriptionDTO.setAppointmentId(appointment.getId());
                prescriptionDTO.setDoctorId(appointment.getDoctorId());
                prescriptionDTO.setPatientId(appointment.getPatientId());
                prescriptionDTO.setPatientFirstName(patientDTO.getFirstName());
                prescriptionDTO.setPatientLastName(patientDTO.getLastName());
                prescriptionFeignClient.addPrescription(prescriptionDTO, userId, true, false, false);
            }
        }
        return AppointmentAdapter.getAppointmentDTO(appointment);
    }


}
