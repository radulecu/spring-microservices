package ro.rasel.spring.microservices.passportservice.service;

import org.springframework.web.bind.annotation.PathVariable;
import ro.rasel.spring.microservices.passportservice.domain.Passport;

import java.util.concurrent.CompletableFuture;

public interface AsyncPassportService {
    CompletableFuture<Passport> getPassport(@PathVariable String userId);
}
