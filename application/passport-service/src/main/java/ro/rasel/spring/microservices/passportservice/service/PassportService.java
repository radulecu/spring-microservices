package ro.rasel.spring.microservices.passportservice.service;

import org.springframework.web.bind.annotation.PathVariable;
import ro.rasel.spring.microservices.passportservice.domain.Passport;

public interface PassportService {
    Passport getPassport(@PathVariable String userId);
}
