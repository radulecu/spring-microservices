package ro.rasel.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@Primary
@ConditionalOnProperty(name = "swagger.proxy", havingValue = "true")
class ProxySwaggerResourceProvider implements SwaggerResourcesProvider {
    private final SwaggerConfigProperties swaggerConfigProperties;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public ProxySwaggerResourceProvider(SwaggerConfigProperties swaggerConfigProperties,
                                        DiscoveryClient discoveryClient) {
        this.swaggerConfigProperties = swaggerConfigProperties;
        this.discoveryClient = discoveryClient;
    }

    private final DiscoveryClient discoveryClient;

    @Override
    public List<SwaggerResource> get() {
        Set<String> blacklist = swaggerConfigProperties.getBlacklist();
        List<SwaggerResource> collect =
                discoveryClient.getServices().stream()
                        .filter(s -> s != null && !blacklist.contains(s))
                        .map(s -> {
                            SwaggerResource swaggerResource = new SwaggerResource();
                            swaggerResource.setName(s);
                            swaggerResource.setLocation("/" + s + "/v2/api-docs");
                            return swaggerResource;
                        }).sorted(Comparator.comparing(SwaggerResource::getName))
                        .collect(Collectors.toList());
        return collect;
    }
}