package com.example.prober.service;

import com.example.prober.dto.oncall.CreateUserDto;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

@Service
@RequiredArgsConstructor
public class OncallService {

    private final RestClient restClient;

    @Value("${oncall.api.users-host}")
    private String usersHost;

    @Value("${oncall.api.users-path}")
    private String usersPath;

    @Value("${oncall.api.users-port}")
    private String usersPort;

    @Timed("oncall.create.user")
    @Counted("oncall.create.user")
    public void createUser(String username) {
        restClient.post()
                .uri(uri -> uri.host(usersHost)
                        .path(usersPath)
                        .port(usersPort)
                        .scheme("http")
                        .build())
                .body(new CreateUserDto(username))
                .retrieve()
                .toBodilessEntity();
    }

    @Timed("oncall.delete.user")
    @Counted("oncall.delete.user")
    public void deleteUser(String username) {
        restClient.delete()
                .uri(uri -> uri.host(usersHost)
                        .path(String.format("%s/%s", usersPath, username))
                        .port(usersPort)
                        .scheme("http")
                        .build())
                .retrieve()
                .toBodilessEntity();
    }
}
