package com.example.hospitalmanager.repository;

import com.example.hospitalmanager.model.Appointment;
import com.example.hospitalmanager.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDoctor(AppUser doctor);
    boolean existsByPatientId(Long patientId);
}