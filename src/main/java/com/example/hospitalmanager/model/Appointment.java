package com.example.hospitalmanager.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private AppUser doctor;

    private LocalDateTime appointmentDate;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    // Custom Builder extension so .appointmentDateTime(...) works seamlessly with Lombok's builder
    public static class AppointmentBuilder {
        private LocalDateTime appointmentDate;

        public AppointmentBuilder appointmentDateTime(LocalDateTime appointmentDateTime) {
            this.appointmentDate = appointmentDateTime;
            return this;
        }
    }

    // Helper alias for services calling getAppointmentDateTime()
    public LocalDateTime getAppointmentDateTime() {
        return appointmentDate;
    }

    public void setAppointmentDateTime(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
}