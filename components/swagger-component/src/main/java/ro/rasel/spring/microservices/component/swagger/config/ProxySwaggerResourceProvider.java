package ro.rasel.spring.microservices.component.swagger.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ro.rasel.spring.microservices.commons.utils.Touple;
import ro.rasel.spring.microservices.component.swagger.config.properties.SwaggerConfigProperties;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@Primary
@ConditionalOnProperty(name = "swagger.proxy", havingValue = "true")
class ProxySwaggerResourceProvider implements SwaggerResourcesProvider {
    private final SwaggerConfigProperties swaggerConfigProperties;

    @Autowired
    public ProxySwaggerResourceProvider(
            SwaggerConfigProperties swaggerConfigProperties,
            DiscoveryClient discoveryClient) {
        this.swaggerConfigProperties = swaggerConfigProperties;
        this.discoveryClient = discoveryClient;
    }

    private final DiscoveryClient discoveryClient;

    @Override
    public List<SwaggerResource> get() {
        Set<String> blacklist = swaggerConfigProperties.getBlacklist();
        return discoveryClient.getServices().stream()
                .filter(s -> s != null && !blacklist.contains(s))
                .map(s -> Optional.of(s)
                        .map(discoveryClient::getInstances)
                        .filter(instances -> !instances.isEmpty())
                        .map(instances -> instances.get(0))
                        .filter(instance -> instance instanceof EurekaDiscoveryClient.EurekaServiceInstance)
                        .map(instance -> (EurekaDiscoveryClient.EurekaServiceInstance) instance)
                        .map(eurekaServiceInstance -> new Touple<>(s, eurekaServiceInstance))
                        .orElse(null))
                .filter(Objects::nonNull)
                .map(touple -> {
                    SwaggerResource swaggerResource = new SwaggerResource();
                    swaggerResource.setName(touple.getP());
                    swaggerResource.setLocation(
                            toApiDocsPath(touple.getQ().getInstanceInfo().getHomePageUrl(), touple.getP()));
                    return swaggerResource;
                }).sorted(Comparator.comparing(SwaggerResource::getName))
                .collect(Collectors.toList());
    }

    private String toApiDocsPath(String homePageUrl, String serviceName) {
        try {
            String path = new URL(homePageUrl).getPath();
            if (!path.endsWith("/")) {
                path += "/";
            }
            return "/" + serviceName + path + "v2/api-docs";
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}