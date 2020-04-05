package ro.rasel.spring.microservices.contactservice.controller.v1.dto;

import io.swagger.annotations.ApiModel;

import java.beans.ConstructorProperties;

@ApiModel(value = "PhoneNumberDetails", description = "Phone Number Details")
public class PhoneNumberRequest extends PhoneNumberDto {
    public PhoneNumberRequest() {
    }

    @ConstructorProperties({"number", "description"})
    public PhoneNumberRequest(String number, String description) {
        super(number, description);
    }

}
