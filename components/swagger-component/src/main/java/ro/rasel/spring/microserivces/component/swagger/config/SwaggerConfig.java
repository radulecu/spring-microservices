package ro.rasel.spring.microserivces.component.swagger.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.rasel.spring.microserivces.component.swagger.config.properties.SwaggerConfigProperties;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Configuration
public class SwaggerConfig {
    private final SwaggerConfigProperties swaggerConfigProperties;

    @Autowired
    public SwaggerConfig(SwaggerConfigProperties swaggerConfigProperties) {
        this.swaggerConfigProperties = swaggerConfigProperties;
    }

    @Bean
    public Docket api() {
        Set<String> applicationJson = new HashSet<>(Arrays.asList(APPLICATION_JSON_VALUE));
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerConfigProperties.isEnabled())
                .consumes(applicationJson)
                .produces(applicationJson)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ro.rasel"))
                .paths(PathSelectors.any())
                .build();
    }
}