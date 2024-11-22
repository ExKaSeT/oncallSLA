package com.example.sla.service;

import com.example.sla.dto.prometheus.PrometheusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PrometheusService {

    private final RestClient restClient;

    @Value("${oncall.sla.prometheus-host}")
    private String prometheusHost;

    public PrometheusResponse request(String query) {
        return restClient.get()
                .uri(prometheusHost, uri -> uri.path("/api/v1/query?query={query}&time={time}")
                        .build(Map.of(
                                "query", query,
                                "time", Instant.now().getEpochSecond()
                        )))
                .retrieve()
                .body(PrometheusResponse.class);
    }
}
