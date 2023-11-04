package rs.ac.bg.fon.silab.mock_exam.infrastructure.email;

import jakarta.mail.MessagingException;
import jakarta.mail.SendFailedException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
}
