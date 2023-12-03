package ro.rasel.spring.microservices.passportservice.service;

import org.springframework.web.bind.annotation.PathVariable;
import ro.rasel.spring.microservices.passportservice.domain.Passport;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public interface AsyncPassportService {
    Future<Passport> getPassport(@PathVariable String userId) throws ExecutionException, InterruptedException;
}
