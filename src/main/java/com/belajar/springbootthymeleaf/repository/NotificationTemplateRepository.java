package com.belajar.springbootthymeleaf.repository;

import com.belajar.springbootthymeleaf.entity.NotificationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate, Long> {

    Optional<NotificationTemplate> findByCodeAndType(String code, String type);

}
