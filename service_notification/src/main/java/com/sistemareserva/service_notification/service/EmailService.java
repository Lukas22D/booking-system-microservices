package com.sistemareserva.service_notification.service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import jakarta.mail.internet.MimeMessage;

@AllArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendHtmlEmail(String reciver, String subject, String message) throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom("luk.look.s.rocha@gmail.com");
        helper.setTo(reciver);
        helper.setSubject(subject);
        helper.setText(message, true);

        mailSender.send(mimeMessage);

    }

    
}
