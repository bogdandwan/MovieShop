package springbootapp.movieclub.service.impl;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import springbootapp.movieclub.service.EmailService;

import java.io.File;
import java.util.List;
import java.util.Properties;

@Data
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {


    private final String username;
    private final String password;


    @Override
    public void sendEmail(String to, String subject, String body, List<File> attachments) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            // Postavljanje teksta poruke
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(body, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // Dodavanje priloga
            if (attachments != null) {
                for (File attachment : attachments) {
                    MimeBodyPart attachPart = new MimeBodyPart();
                    attachPart.attachFile(attachment);
                    multipart.addBodyPart(attachPart);
                }
            }

            message.setContent(multipart);

            // Slanje poruke
            Transport.send(message);

            System.out.println("Email successfully sent.");
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
