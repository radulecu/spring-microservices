//package ro.rasel.spring.microservices.resourcegatewayservice.config;
//
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * Use this for class configuration
// */
//@Configuration
//public class GatewayConfig {
//
//
//    @Bean
//    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route(r -> r.path("/echo-service/**")
//                        .filters(f -> f.rewritePath("/echo-service/(?<path>.*)", "/${path}"))
//                        .uri("https://localhost:8090"))
//                .route(r -> r.path("/bookmark-service/**")
//                        .filters(f -> f.rewritePath("/bookmark-service/(?<path>.*)", "/${path}"))
//                        .uri("https://localhost:8082"))
//                .route(r -> r.path("/contact-service/**")
//                        .filters(f -> f.rewritePath("/contact-service/(?<path>.*)", "/${path}"))
//                        .uri("https://localhost:8083"))
//                .route(r -> r.path("/passport-service/**")
//                        .filters(f -> f.rewritePath("/passport-service/(?<path>.*)", "/${path}"))
//                        .uri("https://localhost:8084"))
//                .build();
//    }
//}
