//package ro.rasel.spring.microservices.resourcegatewayservice.config;
//
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import ro.rasel.spring.microservices.resourcegatewayservice.filter.SwaggerV3BodyGatewayFilterFactory;
//
///**
// * Use this for class configuration
// */
//@Configuration
//public class GatewayConfig {
//
//    @Bean
//    public static RouteLocator routeLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route(r -> r.path("/echo-service/**")
//                        .filters(f -> f.rewritePath("/echo-service/(?<path>.*)", "/${path}")
//                                .rewriteResponseHeader("Location", "/(?<path>.*)", "/echo-service/${path}")
//                                .filter(new SwaggerV3BodyGatewayFilterFactory().apply((Void) null)))
//                        .uri("https://localhost:8090"))
//                .route(r -> r.path("/bookmark-service/**")
//                        .filters(f -> f.rewritePath("/bookmark-service/(?<path>.*)", "/${path}")
//                                .rewriteResponseHeader("Location", "/(?<path>.*)", "/bookmark-service/${path}")
//                                .filter(new SwaggerV3BodyGatewayFilterFactory().apply((Void) null)))
//                        .uri("https://localhost:8082"))
//                .route(r -> r.path("/contact-service/**")
//                        .filters(f -> f.rewritePath("/contact-service/(?<path>.*)", "/${path}")
//                                .rewriteResponseHeader("Location", "/(?<path>.*)", "/contact-service/${path}")
//                                .filter(new SwaggerV3BodyGatewayFilterFactory().apply((Void) null)))
//                        .uri("https://localhost:8083"))
//                .route(r -> r.path("/passport-service/**")
//                        .filters(f -> f.rewritePath("/passport-service/(?<path>.*)", "/${path}")
//                                .rewriteResponseHeader("Location", "/(?<path>.*)", "/passport-service/${path}")
//                                .filter(new SwaggerV3BodyGatewayFilterFactory().apply((Void) null)))
//                        .uri("https://localhost:8084"))
//                .build();
//    }
//
//}
