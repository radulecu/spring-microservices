package ro.rasel.spring.microservices.passportservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ro.rasel.spring.microservices.passportservice.controller.dto.PassportDto;
import ro.rasel.spring.microservices.passportservice.domain.Passport;
import ro.rasel.spring.microservices.passportservice.service.PassportService;

@RestController
public class PassportRestController implements PassportApi {

    private final PassportService passportService;

    public PassportRestController(PassportService passportService) {
        this.passportService = passportService;
    }

    @Override
    public ResponseEntity<PassportDto> getPassport(@PathVariable String userId) {
        final Passport passport = passportService.getPassport(userId);
        return passport == null ? ResponseEntity.notFound().build() :
                ResponseEntity.ok(new PassportDto(passport));
    }
}
