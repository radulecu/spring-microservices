package ro.rasel.spring.microservices.contactservice.controller.v1.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.beans.ConstructorProperties;

@Schema(title = "PhoneNumberDetailsRequest", description = PhoneNumberDto.DESCRIPTION)
public class PhoneNumberDetailsRequest extends PhoneNumberDto {

    public PhoneNumberDetailsRequest() {
    }

    @ConstructorProperties({"number", "description"})
    public PhoneNumberDetailsRequest(String number, String description) {
        super(number, description);
    }

}
