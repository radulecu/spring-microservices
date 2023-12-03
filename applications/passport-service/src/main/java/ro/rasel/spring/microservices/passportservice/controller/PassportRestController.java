package ro.rasel.spring.microservices.passportservice.controller;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ro.rasel.spring.microservices.passportservice.controller.dto.PassportResponse;
import ro.rasel.spring.microservices.passportservice.domain.Passport;
import ro.rasel.spring.microservices.passportservice.service.PassportService;

@RestController
public class PassportRestController implements PassportApi {
    private static final String NAME = "passport";

    private final PassportService passportService;

    public PassportRestController(PassportService passportService) {
        this.passportService = passportService;
    }

    @Override
    @RateLimiter(name = PassportRestController.NAME)
    public ResponseEntity<PassportResponse> getPassport(@PathVariable String userId) {
        final Passport passport = passportService.getPassport(userId);
        return passport == null ? ResponseEntity.notFound().build() :
                ResponseEntity.ok(
                        new PassportResponse(passport.getUserId(), passport.getBookmarks(), passport.getContacts()));
    }
}
