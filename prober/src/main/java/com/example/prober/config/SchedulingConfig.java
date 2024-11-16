package com.example.prober.config;

import com.example.prober.service.OncallService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulingConfig {

    private final OncallService oncallService;

    @Scheduled(cron = "${oncall.probe.user-create-delete-cron}")
    public void createDeleteUser() {
        try {
            oncallService.createUser("kek");
            log.info("User created");
            oncallService.deleteUser("kek");
            log.info("User deleted");
        } catch (Exception ex) {
            log.warn(ex.getMessage());
        }
    }
}
