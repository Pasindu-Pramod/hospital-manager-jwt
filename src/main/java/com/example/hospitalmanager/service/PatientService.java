package com.example.hospitalmanager.service;

import com.example.hospitalmanager.exception.ApiException;
import com.example.hospitalmanager.model.Patient;
import com.example.hospitalmanager.repository.AppointmentRepository;
import com.example.hospitalmanager.repository.MedicalRecordRepository;
import com.example.hospitalmanager.repository.PatientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    public PatientService(PatientRepository patientRepository, 
                          AppointmentRepository appointmentRepository, 
                          MedicalRecordRepository medicalRecordRepository) {
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Patient not found");
        }
        if (appointmentRepository.existsByPatientId(id) || medicalRecordRepository.existsByPatientId(id)) {
            throw new ApiException(HttpStatus.CONFLICT, "Cannot delete patient with existing medical records or appointments");
        }
        patientRepository.deleteById(id);
    }
}