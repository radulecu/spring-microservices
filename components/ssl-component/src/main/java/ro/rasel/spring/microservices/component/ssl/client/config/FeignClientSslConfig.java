//package ro.rasel.spring.microservices.component.ssl.client.config;
//
//import feign.Client;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
//import org.springframework.cloud.openfeign.ribbon.CachingSpringLoadBalancerFactory;
//import org.springframework.cloud.openfeign.ribbon.LoadBalancerFeignClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@ConditionalOnBean(CachingSpringLoadBalancerFactory.class)
//public class FeignClientSslConfig {
//    private final ClientSslContextConfiguration clientSslContextConfiguration;
//
//    @Autowired
//    public FeignClientSslConfig(ClientSslContextConfiguration clientSslContextConfiguration) {
//        this.clientSslContextConfiguration = clientSslContextConfiguration;
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public Client feignClient(
//            CachingSpringLoadBalancerFactory cachingFactory,
//            SpringClientFactory clientFactory) {
//        final Client.Default delegate =
//                new Client.Default(clientSslContextConfiguration.clientSSLContext().getSocketFactory(),
//                        SSLConnectionSocketFactory.getDefaultHostnameVerifier());
//        return new LoadBalancerFeignClient(delegate, cachingFactory, clientFactory);
//    }
//}