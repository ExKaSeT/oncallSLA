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

    @Value("${oncall.api.users}")
    private String usersURI;

    @Timed("oncall.create.user")
    @Counted("oncall.create.user")
    public void createUser(String username) {
        restClient.post()
                .uri(usersURI)
                .body(new CreateUserDto(username))
                .retrieve()
                .toBodilessEntity();
    }

    @Timed("oncall.delete.user")
    @Counted("oncall.delete.user")
    public void deleteUser(String username) {
        restClient.delete()
                .uri(usersURI + "/" + username)
                .retrieve()
                .toBodilessEntity();
    }
}
