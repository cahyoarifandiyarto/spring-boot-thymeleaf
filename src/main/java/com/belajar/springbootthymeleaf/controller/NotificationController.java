package com.belajar.springbootthymeleaf.controller;

import com.belajar.springbootthymeleaf.model.Response;
import com.belajar.springbootthymeleaf.model.SendNotificationRequest;
import com.belajar.springbootthymeleaf.service.NotificationService;
import com.belajar.springbootthymeleaf.util.ResponseUtil;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<Response<Void>> send(@Valid @RequestBody SendNotificationRequest request) throws MessagingException {
        notificationService.send(request);

        Response<Void> response = ResponseUtil.build(null, null, null, HttpStatus.OK);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
