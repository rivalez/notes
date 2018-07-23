package com.tabor.notes.service.project.sharing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailSenderService {
    private JavaMailSender sender;

    @Autowired
    public MailSenderService(JavaMailSender sender) {
        this.sender = sender;
    }

    public void sendEmail(String token, String email, String appUrl) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Project Sharing");
        message.setText("To accept project, please click the link below: \n" + appUrl + "/confirm?token=" + token);
        message.setFrom("noreply@domain.com");

        sender.send(message);
    }
}
