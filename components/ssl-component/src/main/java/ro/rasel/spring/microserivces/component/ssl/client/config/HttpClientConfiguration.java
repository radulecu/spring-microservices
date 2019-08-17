package ro.rasel.spring.microserivces.component.ssl.client.config;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.rasel.spring.microserivces.component.ssl.client.config.properties.ClientSslContextProperties;

@Configuration
public class HttpClientConfiguration {

    private final ClientSslContextConfiguration clientSslContextConfiguration;
    private final ClientSslContextProperties clientSslContextProperties;

    public HttpClientConfiguration(
            ClientSslContextConfiguration clientSslContextConfiguration,
            ClientSslContextProperties clientSslContextProperties) {
        this.clientSslContextConfiguration = clientSslContextConfiguration;
        this.clientSslContextProperties = clientSslContextProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public CloseableHttpClient httpClient() {
        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                clientSslContextConfiguration.clientSSLContext(),
                new String[]{clientSslContextProperties.getProtocol()},
                null,
                (s, sslSession) -> true);

        return HttpClients.custom()
                .setSSLSocketFactory(sslConnectionSocketFactory)
                .build();
    }

}