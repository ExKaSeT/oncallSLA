package com.example.sla.service;

import com.example.sla.dao.IndicatorRepository;
import com.example.sla.model.Indicator;
import com.example.sla.model.IndicatorId;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Gauge;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

@Service
@Slf4j
@RequiredArgsConstructor
public class SlaService {

    private static final List<String> DURATION_METRICS_BASE_NAME = List.of("oncall_delete_user_seconds", "oncall_create_user_seconds");
    Map<String, Gauge> metricNameValue = new HashMap<>();

    private static final int DURATION_SLO_SEC = 1;

    private final PrometheusService prometheusService;
    private final IndicatorRepository indicatorRepository;
    private final CollectorRegistry collectorRegistry;

    public void saveSlaDurationMetrics() {

        for (var metricBaseName : DURATION_METRICS_BASE_NAME) {
            var response = prometheusService
                    .request(String.format("increase(%s_sum{exception=\"none\"}[1m])/increase(%s_count{exception=\"none\"}[1m])",
                            metricBaseName, metricBaseName));
            double value;
            try {
                value = Double.parseDouble(response.getData().getResult().get(0).getValue().get(1));
            } catch (Exception ex) {
                log.error(ex.getMessage());
                continue;
            }
            var gauge = metricNameValue.get(metricBaseName);
            if (isNull(gauge)) {
                gauge = Gauge.build()
                        .name(metricBaseName)
                        .help(metricBaseName)
                        .register(collectorRegistry);
                metricNameValue.put(metricBaseName, gauge);
            }
            gauge.set(value);
            var indicator = new Indicator();
            indicator.setBad(value > DURATION_SLO_SEC);
            indicator.setValue(value);
            indicator.setSlo(DURATION_SLO_SEC);
            indicator.setId(new IndicatorId(metricBaseName, Instant.now().atZone(ZoneId.of("Z"))
                    .toLocalDateTime()));
            indicatorRepository.save(indicator);
        }
    }
}
