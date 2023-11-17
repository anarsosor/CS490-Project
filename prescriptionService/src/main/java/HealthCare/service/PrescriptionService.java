package HealthCare.service;

import HealthCare.dto.*;
import HealthCare.entity.Prescription;
import HealthCare.entity.PrescriptionStatus;
import HealthCare.repository.PrescriptionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PrescriptionService {
    @Autowired
    PrescriptionRepo prescriptionRepo;

    @Autowired
    UserFeignClient userFeignClient;

    @Autowired
    AppointmentFeignClient appointmentFeignClient;

    @Autowired
    PaymentFeignClient paymentFeignClient;

    public PrescriptionDTO getPrescriptionById(long id, long userId, boolean isDoctor, boolean isPatient, boolean isPharmacist) {
        Prescription prescription = prescriptionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("The Prescription not found with ID: " + id));
        AppointmentDTO appointmentDTO = appointmentFeignClient.getAppointmentById(prescription.getAppointmentId(), userId, isDoctor, isPatient, isPharmacist).getBody();
        if(prescription.getPharmacistId() == userId || appointmentDTO.getDoctorId() == userId || appointmentDTO.getPatientId() == userId)
            return PrescriptionAdapter.getPrescriptionDTO(prescription);
        else
            throw new RuntimeException("You are not authorized to get this prescription");
    }

    public List<PrescriptionDTO> getAllPrescriptions() {
        List<Prescription> prescriptionList = prescriptionRepo.findAll();
        List<PrescriptionDTO> prescriptionDTOList = new ArrayList<>();
        prescriptionList.forEach(p -> prescriptionDTOList.add(PrescriptionAdapter.getPrescriptionDTO(p)));
        return prescriptionDTOList;
    }

    public List<PrescriptionDTO> getPrescriptionsByPharmacistId(long pharmacistId) {
        List<Prescription> prescriptionList = prescriptionRepo.findByPharmacistId(pharmacistId);
        List<PrescriptionDTO> prescriptionDTOList = new ArrayList<>();
        prescriptionList.forEach(p -> prescriptionDTOList.add(PrescriptionAdapter.getPrescriptionDTO(p)));
        return prescriptionDTOList;
    }

    public List<PrescriptionDTO> getPrescriptionsByPatientId(long patientId) {
        List<Prescription> prescriptionList = prescriptionRepo.findByPatientId(patientId);
        List<PrescriptionDTO> prescriptionDTOList = new ArrayList<>();
        prescriptionList.forEach(p -> prescriptionDTOList.add(PrescriptionAdapter.getPrescriptionDTO(p)));
        return prescriptionDTOList;
    }

    public List<PrescriptionDTO> getPrescriptionsByDoctorId(long doctorId) {
        List<Prescription> prescriptionList = prescriptionRepo.findByDoctorId(doctorId);
        List<PrescriptionDTO> prescriptionDTOList = new ArrayList<>();
        prescriptionList.forEach(p -> prescriptionDTOList.add(PrescriptionAdapter.getPrescriptionDTO(p)));
        return prescriptionDTOList;
    }

    public List<PrescriptionDTO> getPrescriptionsByAppointmentId(
            long appointmentId, long userId, boolean isDoctor, boolean isPatient, boolean isPharmacist) {
        AppointmentDTO appointment = appointmentFeignClient.getAppointmentById(appointmentId, userId, isDoctor, isPatient, isPharmacist).getBody();
        if (appointment.getPatientId() == userId || appointment.getDoctorId() == userId) {
            List<Prescription> prescriptionList = prescriptionRepo.findByAppointmentId(appointmentId);
            List<PrescriptionDTO> prescriptionDTOList = new ArrayList<>();
            prescriptionList.forEach(p -> prescriptionDTOList.add(PrescriptionAdapter.getPrescriptionDTO(p)));
            return prescriptionDTOList;
        }
        else throw new RuntimeException("You are not authorized to get this prescription");
    }

    public List<PrescriptionDTO> getPrescriptionsByAppointmentIdAndPharmacistId(
            long appointmentId, long userId, boolean isDoctor, boolean isPatient, boolean isPharmacist) {
        List<Prescription> prescriptionList = prescriptionRepo.findByAppointmentIdAndPharmacistId(appointmentId, userId);
        List<PrescriptionDTO> prescriptionDTOList = new ArrayList<>();
        prescriptionList.forEach(p -> prescriptionDTOList.add(PrescriptionAdapter.getPrescriptionDTO(p)));
        return prescriptionDTOList;
    }

    public PrescriptionDTO addPrescription(PrescriptionDTO prescriptionDTO, long userId, boolean isDoctor, boolean isPatient, boolean isPharmacist) {
        AppointmentDTO appointmentDTO = appointmentFeignClient.getAppointmentById(prescriptionDTO.getAppointmentId(), userId, isDoctor, isPatient, isPharmacist).getBody();
        if ((appointmentDTO.getDoctorId() != prescriptionDTO.getDoctorId()) || appointmentDTO.getPatientId() != prescriptionDTO.getPatientId())
            throw new RuntimeException("You are not authorized to add this prescription. check appointmentId or patientId");
        userFeignClient.getPharmacistById(prescriptionDTO.getPharmacistId());   // check pharmacist existence
        Prescription prescription = PrescriptionAdapter.getPrescription(prescriptionDTO);
        return PrescriptionAdapter.getPrescriptionDTO(prescriptionRepo.save(prescription));
    }

    public PrescriptionDTO deliverPrescription(PrescriptionDeliveryDTO prescriptionDeliveryDTO, long userId) {
        Prescription prescription = prescriptionRepo.findById(prescriptionDeliveryDTO.getId())
                .orElseThrow(() -> new RuntimeException("The Prescription not found with ID: " + prescriptionDeliveryDTO.getId()));
        if (prescription.getPharmacistId() != userId)
            throw new RuntimeException("You are not authorized to deliver this prescription");
        if (prescription.getStatus() == PrescriptionStatus.DELIVERED)
            throw new RuntimeException("The prescription has already been delivered");
        prescription.setStatus(PrescriptionStatus.DELIVERED);
        prescription.setNetPayment(prescriptionDeliveryDTO.getNetPayment());
        prescription = prescriptionRepo.save(prescription);

        List<Prescription> prescriptionList = prescriptionRepo.findByAppointmentIdAndStatus(prescription.getAppointmentId(), PrescriptionStatus.NEW);
        if(prescriptionList.isEmpty()) {
            DoctorDTO doctorDTO = userFeignClient.getDoctorById(prescription.getDoctorId()).getBody();
            BigDecimal doctorCharge = new BigDecimal(doctorDTO.getHourRate());
            BigDecimal prescriptionCharge = prescriptionRepo.calculateSumOfNetPaymentByAppointmentId(prescription.getAppointmentId());
            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setAppointmentId(prescription.getAppointmentId());
            paymentDTO.setPatientId(prescription.getPatientId());
            paymentDTO.setDoctorCharge(doctorCharge);
            paymentDTO.setPrescriptionCharge(prescriptionCharge);
            paymentFeignClient.addPayment(paymentDTO, userId, false, false, true);
        }
        return PrescriptionAdapter.getPrescriptionDTO(prescription);
    }

}
