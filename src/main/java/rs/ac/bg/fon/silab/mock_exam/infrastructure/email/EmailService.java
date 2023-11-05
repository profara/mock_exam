package rs.ac.bg.fon.silab.mock_exam.infrastructure.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.entity.Appointment;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.entity.Candidate;

import java.time.format.DateTimeFormatter;

@Service
public class EmailService implements EmailSender{

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    @Async
    public void send(String to, String email) {
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Potvrda email adrese");
            helper.setFrom("alekbg99@gmail.com");
            mailSender.send(mimeMessage);
        } catch(MessagingException ex){
            LOGGER.error("Failed to send email", ex);
            throw new IllegalStateException("Greška prilikom slanja mejla");
        }
    }

    @Override
    @Async
    public void sendAttachment(String to, byte[] image) {
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setText("Vašu uplatnicu možete pronaći u prilogu");
            helper.setTo(to);
            helper.setSubject("Uplatnica");
            helper.setFrom("alekbg99@gmail.com");
            helper.addAttachment("Uplatnica.png", new ByteArrayResource(image));
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException("Greška prilikom slanja mejla");
        }
    }

    @Override
    @Async
    public void sendReminderEmail(Candidate candidate, Appointment appointment) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText("Poštovani " + candidate.getName() + ",\n\n" +
                "Želimo da Vas podsetimo da ste se prijavili za polaganje probnog prijemnog ispita koji se održava " +
                appointment.getAppointmentDate().format(formatter) + " u " + appointment.getAppointmentDate().format(timeFormatter) + " časova.\n\n" +
                "Pozdrav"
        );
        message.setTo(candidate.getUserProfile().getEmail());
        message.setSubject("Podsetnik");
        message.setFrom("alekbg99@gmail.com");
        mailSender.send(message);
    }
}
