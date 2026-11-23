package com.example.hospitalmanager.service;

import com.example.hospitalmanager.dto.AppointmentRequest;
import com.example.hospitalmanager.dto.AppointmentResponse;
import com.example.hospitalmanager.exception.ApiException;
import com.example.hospitalmanager.model.Appointment;
import com.example.hospitalmanager.model.AppointmentStatus;
import com.example.hospitalmanager.model.AppUser;
import com.example.hospitalmanager.model.Patient;
import com.example.hospitalmanager.repository.AppointmentRepository;
import com.example.hospitalmanager.repository.UserRepository;
import com.example.hospitalmanager.repository.PatientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, PatientRepository patientRepository, UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
    }

    public AppointmentResponse bookAppointment(AppointmentRequest request) {
        Patient patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Patient not found"));
        AppUser doctor = userRepository.findById(request.doctorId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Doctor not found"));

        Appointment appointment = Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .appointmentDateTime(request.appointmentDateTime())
                .status(AppointmentStatus.SCHEDULED)
                .build();

        Appointment saved = appointmentRepository.save(appointment);
        return mapToResponse(saved);
    }

    public List<AppointmentResponse> getAppointmentsForDoctor(String username) {
        AppUser doctor = userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Doctor not found"));
        return appointmentRepository.findByDoctor(doctor).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private AppointmentResponse mapToResponse(Appointment appt) {
        return new AppointmentResponse(
            appt.getId(),
            appt.getPatient().getId(),
            appt.getPatient().getName(),
            appt.getDoctor().getId(),
            appt.getDoctor().getFullName(),
            appt.getAppointmentDateTime(),
            appt.getStatus()
        );
    }
}