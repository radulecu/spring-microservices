package ro.rasel.spring.microservices.contactservice.controller.v1.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.beans.ConstructorProperties;
import java.util.Objects;
import java.util.StringJoiner;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;
import static ro.rasel.spring.microservices.contactservice.controller.v1.dto.PhoneNumberDto.DESCRIPTION;

@SuppressWarnings("ALL")
@Schema(title = "PhoneNumberDetailsResponse", description = DESCRIPTION)
public class PhoneNumberDetailsResponse extends PhoneNumberDto {
    private Long id;

    public PhoneNumberDetailsResponse() {
    }

    @ConstructorProperties({"id", "number", "description"})
    public PhoneNumberDetailsResponse(Long id, String number, String description) {
        super(number, description);
        this.id = id;
    }

    @NotNull
    @Schema(requiredMode = REQUIRED, example = "64")
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
        PhoneNumberDetailsResponse that = (PhoneNumberDetailsResponse) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PhoneNumberDetailsRequest.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                + " " + super.toString();
    }
}
