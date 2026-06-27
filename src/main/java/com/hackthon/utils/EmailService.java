package com.hackthon.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void entraideEmailSender(String to, String subject, String htmlContent) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("isabellecomp@gmail.com"); // par ex.
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // true = HTML

            mailSender.send(message);
        } catch (MessagingException e) {
            System.err.println("Erreur lors de l'envoi du mail : " + e.getMessage());
            throw new RuntimeException("Erreur lors de l'envoi du mail", e);
        }
    }

    public void sendEmailtoConfirmYourProposal(String to,String from, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("isabellecomp@gmail.com"); // met ton adresse ici
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
