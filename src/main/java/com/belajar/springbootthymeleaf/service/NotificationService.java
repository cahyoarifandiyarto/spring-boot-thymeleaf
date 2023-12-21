package com.belajar.springbootthymeleaf.service;

import com.belajar.springbootthymeleaf.model.SendNotificationRequest;
import jakarta.mail.MessagingException;

public interface NotificationService {

    void send(SendNotificationRequest request) throws MessagingException;

}
