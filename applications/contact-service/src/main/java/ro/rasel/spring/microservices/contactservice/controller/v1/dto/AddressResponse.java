package ro.rasel.spring.microservices.contactservice.controller.v1.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.beans.ConstructorProperties;
import java.util.Objects;
import java.util.StringJoiner;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Schema(description = "Address")
public class AddressResponse extends AddressDto {
    private Long id;

    AddressResponse() {
    }

    @ConstructorProperties(
            {"id", "country", "town", "street", "number", "entrance", "flatNumber"})
    public AddressResponse(
            Long id, String country, String town, String street, int number, Integer entrance, Integer flatNumber) {
        super(country, town, street, number, entrance, flatNumber);
        this.id = id;
    }

    @Schema(requiredMode = REQUIRED, example = "31")
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        AddressResponse that = (AddressResponse) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AddressResponse.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                + " " + super.toString();
    }
}
