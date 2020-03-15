package ro.rasel.spring.microservices.contactservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonBeansConfig {
    @Bean
    public static ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
