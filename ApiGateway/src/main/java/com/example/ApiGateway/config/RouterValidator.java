package com.example.ApiGateway.config;

import java.util.List;
import java.util.function.Predicate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouterValidator {

    public static final List<String> openApiEndpoints = List.of(
            "api/auth/register",
            "api/auth/authenticate",
            "/swagger-ui/index.html",
            "/api",
            "/swagger-ui.html",
            "/swagger-ui/*",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**"

    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));


}