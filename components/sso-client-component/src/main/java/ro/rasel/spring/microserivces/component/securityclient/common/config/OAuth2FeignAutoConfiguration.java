package ro.rasel.spring.microserivces.component.securityclient.common.config;

import feign.Feign;
import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

@Configuration
@ConditionalOnClass({Feign.class})
//@ConditionalOnProperty(value = "feign.oauth2.enabled", matchIfMissing = true)
public class OAuth2FeignAutoConfiguration {

    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor(
            OAuth2ClientContext oauth2ClientContext,
            OAuth2ProtectedResourceDetails resource) {
        return new OAuth2FeignRequestInterceptor(oauth2ClientContext, resource);
    }
}