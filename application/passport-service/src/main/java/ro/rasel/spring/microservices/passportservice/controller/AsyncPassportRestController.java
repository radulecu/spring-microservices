package ro.rasel.spring.microservices.passportservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ro.rasel.spring.microservices.passportservice.controller.dto.PassportResponse;
import ro.rasel.spring.microservices.passportservice.service.AsyncPassportService;

import java.util.concurrent.Future;

@RestController
public class AsyncPassportRestController implements AsyncPassportApi {

    private final AsyncPassportService passportService;

    public AsyncPassportRestController(AsyncPassportService passportService) {
        this.passportService = passportService;
    }

    @Override
    public Future<ResponseEntity<PassportResponse>> getPassport(@PathVariable String userId) {
        return passportService.getPassport(userId).thenApply(passport ->
                passport == null ? ResponseEntity.notFound().build() :
                        ResponseEntity.ok(new PassportResponse(passport.getUserId(), passport.getBookmarks(),
                                passport.getContacts())));
    }
}
