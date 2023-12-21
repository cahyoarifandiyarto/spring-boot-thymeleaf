package com.belajar.springbootthymeleaf.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "notification_template")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class NotificationTemplate {

    @Id
    private Long id;

    private String code;

    private String type;

    private String subject;

    private String content;

}
