package springbootapp.movieclub.service;

import java.io.File;
import java.util.List;

public interface EmailService {

    void sendEmail(String to, String subject, String body, List<File> attachments);

}
