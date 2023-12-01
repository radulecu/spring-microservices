package ro.rasel.spring.microservices.component.swagger.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.composite.CompositeDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import ro.rasel.spring.microservices.common.utils.Touple;
import ro.rasel.spring.microservices.component.swagger.config.properties.SwaggerConfigProperties;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
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
    private final String applicationName;

    @Autowired
    public ProxySwaggerResourceProvider(
            SwaggerConfigProperties swaggerConfigProperties,
            Environment environment,
            DiscoveryClient rootDiscoveryClient) {
        this.swaggerConfigProperties = swaggerConfigProperties;
        this.applicationName = environment.getProperty("spring.application.name");
        this.rootDiscoveryClient = rootDiscoveryClient;
    }

    private final DiscoveryClient rootDiscoveryClient;

    @Override
    public List<SwaggerResource> get() {
        Set<String> blacklist = swaggerConfigProperties.getBlacklist();
        List<SwaggerResource> swaggerResources = Optional.ofNullable(rootDiscoveryClient)
                .filter(t -> t instanceof CompositeDiscoveryClient)
                .map(t -> ((CompositeDiscoveryClient) t).getDiscoveryClients())
                .flatMap(discoveryClients -> discoveryClients.stream()
                        .filter(discoveryClient -> discoveryClient instanceof EurekaDiscoveryClient)
                        .map(discoveryClient -> (EurekaDiscoveryClient) discoveryClient)
                        .findAny())
                .map(discoveryClient -> discoveryClient
                        .getServices()
                        .stream()
                        .filter(s -> s != null && !blacklist.contains(s))
                        .map(s -> Optional.of(s)
                                .map(rootDiscoveryClient::getInstances)
                                .filter(instances -> !instances.isEmpty())
                                .map(instances -> instances.get(0))
                                .map(eurekaServiceInstance -> new Touple<>(s, eurekaServiceInstance))
                                .orElse(null))
                        .filter(Objects::nonNull)
                        .map(touple -> {
                            SwaggerResource swaggerResource = new SwaggerResource();
                            swaggerResource.setName(touple.getP());
                            swaggerResource.setLocation(
//                            toApiDocsPath(touple.getP(), touple.getQ().getInstanceInfo().getHomePageUrl()));
                                    toApiDocsPath(touple.getP(), touple.getQ().getUri().toString()));
                            return swaggerResource;
                        }).sorted(Comparator.comparing(SwaggerResource::getName))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
        return swaggerResources;
    }

    private String toApiDocsPath(String serviceName, String homePageUrl) {
        if (serviceName.equals(this.applicationName)) {
            return "/v2/api-docs";
        }

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