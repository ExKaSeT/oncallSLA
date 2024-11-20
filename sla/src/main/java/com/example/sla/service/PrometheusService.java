package com.example.sla.service;

import com.example.sla.dto.prometheus.PrometheusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class PrometheusService {

    private final RestClient restClient;

    @Value("${oncall.sla.prometheus-host}")
    private String prometheusHost;

    public PrometheusResponse request(String query) {
        return restClient.get()
                .uri(uri -> uri.host(prometheusHost).path("/api/v1/query")
                        .queryParam("query", query)
                        .queryParam("time", Instant.now())
                        .build())
                .retrieve()
                .body(PrometheusResponse.class);
    }
}
