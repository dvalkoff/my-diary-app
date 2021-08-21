package ru.valkov.spring.mydiaryapp.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderImpl implements EmailSender {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailSenderImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(String to, String email) throws IllegalStateException {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

            messageHelper.setText(email, true);
            messageHelper.setTo(to);
            messageHelper.setSubject("Activate your account");
            messageHelper.setFrom("ER_radd@mail.ru");

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}
