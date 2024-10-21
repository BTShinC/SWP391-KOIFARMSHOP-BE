package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.model.EmailDetail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

    @Autowired
    TemplateEngine templateEngine;

    @Autowired
    JavaMailSender javaMailSender;

    // Gửi email với Thymeleaf template
    public void sendEmail(EmailDetail emailDetail, String templateName) {
        try {
            // Set biến vào template context (name, link...)
            Context context = new Context();
            context.setVariable("name", emailDetail.getReceiver().getUsername());
            context.setVariable("link", emailDetail.getLink());
            context.setVariable("button", "Reset Password");

            // Xử lý template và render HTML nội dung
            String htmlContent = templateEngine.process(templateName, context);

            // Tạo và cấu hình email
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom("chungdmse171186@fpt.edu.vn");
            mimeMessageHelper.setTo(emailDetail.getReceiver().getEmail());
            mimeMessageHelper.setSubject(emailDetail.getSubject());
            mimeMessageHelper.setText(htmlContent, true);  // true để gửi email dạng HTML

            // Gửi email
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            System.out.println("Error sending email: " + e.getMessage());
        }
    }
}
