package com.belajar.springbootthymeleaf.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SendNotificationRequest {

    @NotBlank
    private String notificationType;

    @NotBlank
    private String templateCode;

    @NotBlank
    private String to;

    @NotNull
    private Map<String, Object> data;

}
