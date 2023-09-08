package rs.ac.bg.fon.silab.mock_exam.domain.appointment.service;


import rs.ac.bg.fon.silab.mock_exam.domain.appointment.entity.Appointment;

public interface AppointmentService {

    Appointment getById(Long id);
}
