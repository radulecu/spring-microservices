package ro.rasel.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@EnableSwagger2
@ComponentScan
public class SwaggerConfig {
    private SwaggerConfigProperties swaggerConfigProperties;

    @Autowired
    public SwaggerConfig(SwaggerConfigProperties swaggerConfigProperties) {
        this.swaggerConfigProperties = swaggerConfigProperties;
    }

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerConfigProperties.isEnabled())
                .select()
                .apis(RequestHandlerSelectors.basePackage("ro.rasel"))
                .paths(PathSelectors.any())
                .build();
    }

    @Configuration
    @Primary
    @ConditionalOnProperty(name = "swagger.proxy", havingValue = "true")
    class PropertyResourceProvider implements SwaggerResourcesProvider {
        @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
        @Autowired
        public PropertyResourceProvider(DiscoveryClient discoveryClient) {
            this.discoveryClient = discoveryClient;
        }

        private DiscoveryClient discoveryClient;

        @Override
        public List<SwaggerResource> get() {
            Set<String> blacklist = swaggerConfigProperties.getBlacklist();
            List<SwaggerResource> collect =
                    discoveryClient.getServices().stream().filter(s -> s != null && !blacklist.contains(s))
                            .map(s -> {
                                SwaggerResource swaggerResource = new SwaggerResource();
                                swaggerResource.setName(s);
                                swaggerResource.setLocation("/" + s + "/v2/api-docs");
                                return swaggerResource;
                            }).collect(Collectors.toList());
            return collect;
        }
    }
}