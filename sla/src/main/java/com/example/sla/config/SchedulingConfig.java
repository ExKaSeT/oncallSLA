package com.example.sla.config;

import com.example.sla.service.SlaService;
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

    private final SlaService slaService;

    @Scheduled(cron = "${oncall.sla.cron}")
    public void getDurationMetrics() {
        try {
            slaService.saveSlaDurationMetrics();
            log.info("SLA подсчитан");
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }
}
