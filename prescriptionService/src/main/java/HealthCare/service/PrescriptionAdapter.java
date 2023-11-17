package HealthCare.service;

import HealthCare.dto.PrescriptionDTO;
import HealthCare.entity.Prescription;

public class PrescriptionAdapter {
    public static PrescriptionDTO getPrescriptionDTO(Prescription prescription) {
        return new PrescriptionDTO(prescription.getId(), prescription.getAppointmentId(), prescription.getDoctorId(),
                prescription.getPharmacistId(), prescription.getPatientId(), prescription.getPatientFirstName(),
                prescription.getPatientLastName(), prescription.getCreationDate(), prescription.getDrugList(),
                prescription.getStatus(), prescription.getNetPayment());
    }

    public static Prescription getPrescription(PrescriptionDTO prescriptionDTO) {
        return new Prescription(prescriptionDTO.getAppointmentId(), prescriptionDTO.getDoctorId(),
                prescriptionDTO.getPharmacistId(), prescriptionDTO.getPatientId(), prescriptionDTO.getPatientFirstName(),
                prescriptionDTO.getPatientLastName(), prescriptionDTO.getDrugList());
    }
}
