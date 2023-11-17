package rs.ac.bg.fon.silab.mock_exam.infrastructure.reminder;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.entity.Appointment;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.entity.Candidate;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.repository.CandidateRepository;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.email.EmailSender;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for sending automated reminders for appointments.
 * This service is responsible for identifying appointments that are due in a specific timeframe
 * and sending reminder emails to the corresponding candidates.
 */
@Service
public class ReminderService {

    private final CandidateRepository candidateRepository;
    private final EmailSender emailSender;

    /**
     * Constructs a ReminderService with necessary dependencies.
     *
     * @param candidateRepository Repository for accessing candidate data.
     * @param emailSender Service for sending emails.
     */
    public ReminderService(CandidateRepository candidateRepository, EmailSender emailSender) {
        this.candidateRepository = candidateRepository;
        this.emailSender = emailSender;
    }

    /**
     * Scheduled task to send appointment reminders to candidates.
     * This method runs daily and checks for appointments that are scheduled
     * two days from the current time, sending reminders to the respective candidates.
     */
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
