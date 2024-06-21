package sn.mail.emailapplication.controller;


import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class EmailController {
    private static final Logger logger = Logger.getLogger(EmailController.class.getName());

    @PostMapping("/sendEmail")
    public String sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String body) {
        // Configuration des propriétés SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "10.142.76.184");
        properties.put("mail.smtp.port", "25");

        // Open message session
        Session session = Session.getInstance(properties);

        try {
            // Object MimeMessage
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("info@axa.jh"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(body);

            // send message
            Transport.send(message);
            logger.log(Level.INFO, "Email envoyé avec succès à " + to);
            return "Email envoyé avec succès à " + to;

        } catch (MessagingException e) {
            logger.log(Level.SEVERE, "Erreur lors de l'envoi de l'email", e);
            return "Erreur lors de l'envoi de l'email: " + e.getMessage();
        }
    }

}
