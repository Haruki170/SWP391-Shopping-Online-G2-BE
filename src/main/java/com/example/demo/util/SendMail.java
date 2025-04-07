package com.example.demo.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class SendMail {
    @Autowired
    private JavaMailSender mailSender;

    public boolean sentMailRegister(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("pna2906@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        mailSender.send(message);
        return true;
    }

    public  boolean sendMailShopRegister(String to, String subject, String email, String password)  {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            // Đọc nội dung file HTML
            String content = new String(Files.readAllBytes(Paths.get("src/main/resources/templates/email_register.html")));
            System.out.println(content);
            content = content.replace("{{email}}", email);
            content = content.replace("{{password}}", password);

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("pna2906@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            // Gửi nội dung HTML
            helper.setText(content, true);
            mailSender.send(mimeMessage);
            System.out.println("Gửi mail thành công với HTML từ file");
            return true;
        } catch (MessagingException | IOException e) {
            System.out.println("Lỗi khi gửi mail: " + e.getMessage());
        }
        return false;
    }

}
