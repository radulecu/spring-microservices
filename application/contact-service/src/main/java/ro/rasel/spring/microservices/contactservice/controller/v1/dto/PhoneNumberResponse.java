package ro.rasel.spring.microservices.contactservice.controller.v1.dto;

import io.swagger.annotations.ApiModel;

import java.beans.ConstructorProperties;
import java.util.Objects;
import java.util.StringJoiner;

@ApiModel(value = "PhoneNumberDetails", description = "Phone Number Details")
public class PhoneNumberResponse extends PhoneNumberDto {
    private Long id;

    public PhoneNumberResponse() {
    }

    @ConstructorProperties({"id", "number", "description"})
    public PhoneNumberResponse(Long id, String number, String description) {
        super(number, description);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        PhoneNumberResponse that = (PhoneNumberResponse) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PhoneNumberRequest.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .toString()
                + " " + super.toString();
    }
}
