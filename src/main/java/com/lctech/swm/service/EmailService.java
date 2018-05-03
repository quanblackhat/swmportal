package com.lctech.swm.service;

import com.lctech.swm.domain.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.ISpringTemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private ISpringTemplateEngine templateEngine;


    private void sendSimpleMessage(Mail mail, String template) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        Context context = new Context();
        context.setVariables(mail.getModel());
        String html = templateEngine.process(template, context);

        helper.setTo(mail.getTo());
        helper.setText(html, true);
        helper.setSubject(mail.getSubject());
        helper.setFrom(mail.getFrom());

        emailSender.send(message);
    }

    public void sendEmailAfterCreatedUser(List<String> receives, String username) {
        receives.forEach(receive -> {
            Mail mail = new Mail();
            mail.setFrom("no-reply@memorynotfound.com");
            mail.setTo(receive);
            mail.setSubject("Thêm tài khoản thành công");

            Map<String, Object> model = new HashMap<>();
            model.put("username", username);
            mail.setModel(model);

            try {
                this.sendSimpleMessage(mail, "create-user-email");
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            LOGGER.info(String.format("Send Email to %s!", receive));
        });

    }

}
