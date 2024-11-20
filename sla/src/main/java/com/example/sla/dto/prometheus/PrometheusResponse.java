package com.example.sla.dto.prometheus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrometheusResponse {
    private String status;
    private DataDto data;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DataDto {
        private String resultType;
        private List<ResultDto> result;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResultDto {
        private Map<String, String> metric;
        private List<String> value;
    }
}
