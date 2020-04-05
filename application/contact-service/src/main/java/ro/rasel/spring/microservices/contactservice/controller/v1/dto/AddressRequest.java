package ro.rasel.spring.microservices.contactservice.controller.v1.dto;

import io.swagger.annotations.ApiModel;

import java.beans.ConstructorProperties;

@ApiModel(description = "Address")
public class AddressRequest extends AddressDto {

    @ConstructorProperties({"country", "town", "street", "number", "entrance", "flatNumber"})
    public AddressRequest(
            String country, String town, String street, int number, Integer entrance, Integer flatNumber) {
        super(country, town, street, number, entrance, flatNumber);
    }
}
