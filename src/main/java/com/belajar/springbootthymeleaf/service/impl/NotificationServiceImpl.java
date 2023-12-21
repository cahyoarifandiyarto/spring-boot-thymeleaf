package com.belajar.springbootthymeleaf.service.impl;

import com.belajar.springbootthymeleaf.entity.NotificationTemplate;
import com.belajar.springbootthymeleaf.exception.ErrorException;
import com.belajar.springbootthymeleaf.model.SendNotificationRequest;
import com.belajar.springbootthymeleaf.repository.NotificationTemplateRepository;
import com.belajar.springbootthymeleaf.service.NotificationService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationTemplateRepository notificationTemplateRepository;

    private final TemplateEngine templateEngine;

    private final JavaMailSender javaMailSender;

    private final RestTemplate restTemplate;

    @Value("${mail.from}")
    private String mailFrom;

    @Value("${sms-gateway.send-sms-url}")
    private String smsGatewaySendSmsUrl;

    @Override
    @Async
    public void send(SendNotificationRequest request) throws MessagingException {
        NotificationTemplate notificationTemplate = notificationTemplateRepository.findByCodeAndType(request.getTemplateCode(), request.getNotificationType())
                .orElseThrow(() -> new ErrorException(HttpStatus.NOT_FOUND));

        Context context = new Context();
        context.setVariables(request.getData());

        String content = templateEngine.process(notificationTemplate.getContent(), context);
        log.info("Content : {}", content);

        if (request.getNotificationType().equals("EMAIL")) {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
            mimeMessageHelper.setFrom(mailFrom);
            mimeMessageHelper.setTo(request.getTo());
            mimeMessageHelper.setSubject(notificationTemplate.getSubject());
            mimeMessageHelper.setText(content, true);

            javaMailSender.send(mimeMessage);
        }

        if (request.getNotificationType().equals("SMS")) {

            String url = smsGatewaySendSmsUrl.replace("{mobile}", request.getTo())
                    .replace("{message}", content);

            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, HttpEntity.EMPTY, String.class);
            log.info("Send SMS to SMS Gateway Response : {}", responseEntity);
        }
    }

}
