package ro.rasel.spring.microservices.passportservice.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ro.rasel.spring.microservices.api.bookmark.data.Bookmark;
import ro.rasel.spring.microservices.api.contact.data.Contact;
import ro.rasel.spring.microservices.passportservice.domain.Passport;

import java.util.Collection;
import java.util.StringJoiner;

@ApiModel(value = "Passport", description = "User Passport")
public class PassportDto {
    @JsonIgnore
    private Passport passport;

    public PassportDto(Passport passport) {
        this.passport = passport;
    }

    @ApiModelProperty(value = "Passport user id")
    public String getUserId() {
        return passport.getUserId();
    }

    @ApiModelProperty(value = "Passport bookmark")
    public Collection<Bookmark> getBookmarks() {
        return passport.getBookmarks();
    }

    @ApiModelProperty(value = "Passport contacts")
    public Collection<Contact> getContacts() {
        return passport.getContacts();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PassportDto.class.getSimpleName() + "[", "]")
                .add("passport=" + passport)
                .toString();
    }
}
