package ro.rasel.swagger.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.rasel.swagger.config.properties.SwaggerConfigProperties;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    private final SwaggerConfigProperties swaggerConfigProperties;

    @Autowired
    public SwaggerConfig(SwaggerConfigProperties swaggerConfigProperties) {
        this.swaggerConfigProperties = swaggerConfigProperties;
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerConfigProperties.isEnabled())
                .select()
                .apis(RequestHandlerSelectors.basePackage("ro.rasel"))
                .paths(PathSelectors.any())
                .build();
    }
}