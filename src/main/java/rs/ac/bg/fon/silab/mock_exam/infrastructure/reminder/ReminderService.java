package rs.ac.bg.fon.silab.mock_exam.infrastructure.reminder;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.entity.Appointment;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.entity.Candidate;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.repository.CandidateRepository;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.email.EmailSender;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReminderService {

    private final CandidateRepository candidateRepository;
    private final EmailSender emailSender;

    public ReminderService(CandidateRepository candidateRepository, EmailSender emailSender) {
        this.candidateRepository = candidateRepository;
        this.emailSender = emailSender;
    }

    @Scheduled(cron = "0 0 7 * * ?")
    public void sendAppointmentReminders(){
        LocalDateTime startOfTwoDaysFromNow = LocalDateTime.now().plusDays(2).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfTwoDaysFromNow = startOfTwoDaysFromNow.plusDays(1);

        List<Object[]> candidatesAndAppointment = candidateRepository.findAllWithAppointmentBetween(startOfTwoDaysFromNow, endOfTwoDaysFromNow);

        for(Object[] candidateAndAppointment : candidatesAndAppointment){
            Candidate candidate = (Candidate) candidateAndAppointment[0];
            Appointment appointment = (Appointment) candidateAndAppointment[1];
            emailSender.sendReminderEmail(candidate, appointment);
        }
    }



}
