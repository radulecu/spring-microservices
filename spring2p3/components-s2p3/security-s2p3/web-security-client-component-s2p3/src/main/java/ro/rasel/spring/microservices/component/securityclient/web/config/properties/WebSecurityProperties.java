package ro.rasel.spring.microservices.component.securityclient.web.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("security.web")
public class WebSecurityProperties {
    private String urlAntMatcher = "/**";

    public String getUrlAntMatcher() {
        return urlAntMatcher;
    }

    public void setUrlAntMatcher(String urlAntMatcher) {
        this.urlAntMatcher = urlAntMatcher;
    }
}
