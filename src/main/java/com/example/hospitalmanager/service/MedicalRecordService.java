package com.example.hospitalmanager.service;

import com.example.hospitalmanager.dto.MedicalRecordRequest;
import com.example.hospitalmanager.dto.MedicalRecordResponse;
import com.example.hospitalmanager.exception.ApiException;
import com.example.hospitalmanager.model.AppUser;
import com.example.hospitalmanager.model.MedicalRecord;
import com.example.hospitalmanager.model.Patient;
import com.example.hospitalmanager.repository.MedicalRecordRepository;
import com.example.hospitalmanager.repository.PatientRepository;
import com.example.hospitalmanager.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository recordRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    public MedicalRecordService(MedicalRecordRepository recordRepository, PatientRepository patientRepository, UserRepository userRepository) {
        this.recordRepository = recordRepository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
    }

    public MedicalRecordResponse createRecord(MedicalRecordRequest request, String doctorUsername) {
        Patient patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Patient not found"));
        AppUser doctor = userRepository.findByUsername(doctorUsername)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Doctor not found"));

        MedicalRecord record = MedicalRecord.builder()
                .patient(patient)
                .doctor(doctor)
                .diagnosis(request.diagnosis())
                .prescription(request.prescription())
                .createdAt(LocalDateTime.now())
                .build();

        MedicalRecord saved = recordRepository.save(record);
        return new MedicalRecordResponse(
            saved.getId(),
            patient.getId(),
            patient.getName(),
            doctor.getId(),
            doctor.getFullName(),
            saved.getDiagnosis(),
            saved.getPrescription(),
            saved.getCreatedAt()
        );
    }
}