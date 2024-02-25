package ro.rasel.spring.microservices.component.securityclient.resource.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("security.resource")
public class ResourceSecurityProperties {
    private String urlAntMatcher = "/**";

    public String getUrlAntMatcher() {
        return urlAntMatcher;
    }

    public void setUrlAntMatcher(String urlAntMatcher) {
        this.urlAntMatcher = urlAntMatcher;
    }
}
