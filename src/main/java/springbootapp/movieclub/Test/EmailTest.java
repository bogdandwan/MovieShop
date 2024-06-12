package springbootapp.movieclub.Test;

import lombok.RequiredArgsConstructor;
import springbootapp.movieclub.service.EmailService;
import springbootapp.movieclub.service.impl.EmailServiceImpl;

import java.io.File;
import java.util.Arrays;
import java.util.List;
@RequiredArgsConstructor
public class EmailTest {

    public static void main(String[] args) {




        String username = "bubanja.bogdan95@gmail.com"; // Vaša Gmail adresa
        String password = "Bolisimo123"; // Vaša Gmail lozinka
        String to = "bogdan.bubanja@gmail.com"; // Adresa primaoca
        String subject = "Test Email";
        String body = "This is a test email.";
        List<File> attachments = Arrays.asList(new File("attachment.txt"));

        EmailServiceImpl emailService = new EmailServiceImpl(username, password);

        emailService.sendEmail(to, subject, body, attachments);

    }

}
