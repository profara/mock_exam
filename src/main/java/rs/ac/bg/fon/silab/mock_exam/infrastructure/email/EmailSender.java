package rs.ac.bg.fon.silab.mock_exam.infrastructure.email;

import rs.ac.bg.fon.silab.mock_exam.domain.appointment.entity.Appointment;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.entity.Candidate;

/**
 * Interface for email sending functionalities.
 * Provides methods for sending simple emails, emails with attachments,
 * and specific reminder emails related to appointments.
 */
public interface EmailSender {

    /**
     * Sends a simple email to the specified recipient.
     *
     * @param to    the email address of the recipient
     * @param email the content of the email to be sent
     * @throws IllegalStateException if there was an error while sending an email
     */
    void send(String to, String email);

    /**
     * Sends an email with an attachment to the specified recipient.
     *
     * @param to     the email address of the recipient
     * @param image  the byte array representing the attachment (e.g., an image)
     * @throws IllegalStateException if there was an error while sending an email
     */
    void sendAttachment(String to, byte[] image);

    /**
     * Sends a reminder email to a candidate about an upcoming appointment.
     * The email includes details about the appointment.
     *
     * @param candidate   the candidate to whom the reminder is sent
     * @param appointment the appointment for which the reminder is being sent
     */
    void sendReminderEmail(Candidate candidate, Appointment appointment);
}
