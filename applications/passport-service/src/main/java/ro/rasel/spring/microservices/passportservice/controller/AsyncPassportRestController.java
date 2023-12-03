package ro.rasel.spring.microservices.passportservice.controller;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ro.rasel.spring.microservices.passportservice.controller.dto.PassportResponse;
import ro.rasel.spring.microservices.passportservice.domain.Passport;
import ro.rasel.spring.microservices.passportservice.service.AsyncPassportService;
import ro.rasel.spring.microservices.passportservice.service.PassportService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class AsyncPassportRestController implements AsyncPassportApi {
    private static final String NAME = "async-passport";

    private final AsyncPassportService passportService;

    public AsyncPassportRestController(AsyncPassportService passportService) {
        this.passportService = passportService;
    }

    @Override
    @RateLimiter(name= AsyncPassportRestController.NAME)
    public Future<ResponseEntity<PassportResponse>> getPassport(@PathVariable String userId)
            throws ExecutionException, InterruptedException {
        Passport passport = passportService.getPassport(userId).get();
        return AsyncResult.forValue(
                passport == null ? ResponseEntity.notFound().build() :
                        ResponseEntity.ok(new PassportResponse(passport.getUserId(), passport.getBookmarks(),
                                passport.getContacts())));
    }
}
