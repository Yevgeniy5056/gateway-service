package com.cydeo.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, AuthHeaderFilter authHeaderFilter) {

        return builder.routes()
                .route("user-service", r -> r
                        .path("/user-service/**")
                        .filters(f -> f
                                .filter(authHeaderFilter)
                                .rewritePath("/user-service/(?<path>.*)", "/${path}"))
                        .uri("lb://user-service"))
                .route("project-service", r -> r
                        .path("/project-service/**")
                        .filters(f -> f
                                .filter(authHeaderFilter)
                                .rewritePath("/project-service/(?<path>.*)", "/${path}"))
                        .uri("lb://project-service"))
                .route("task-service", r -> r
                        .path("/task-service/**")
                        .filters(f -> f
                                .filter(authHeaderFilter)
                                .rewritePath("/task-service/(?<path>.*)", "/${path}"))
                        .uri("lb://task-service"))
                .build();

    }

}
