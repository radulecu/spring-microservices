package ro.rasel.spring.microservices.contactservice.controller.v1.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.StringJoiner;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public class PhoneNumberDto {
    public static final String DESCRIPTION = "Phone number details";
    private String number;

    private String description;

    PhoneNumberDto() {
    }

    public PhoneNumberDto(String number, String description) {
        this.number=number;
        this.description=description;
    }

    @NotBlank
    @Schema(requiredMode = REQUIRED,example = "+10723123456")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Size(min = 1, message = "PhoneNumber description should not be blank")
    @Schema(example = "phone number description Stéphane Maldini")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PhoneNumberDto that = (PhoneNumberDto) o;
        return Objects.equals(number, that.number) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, description);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PhoneNumberDto.class.getSimpleName() + "[", "]")
                .add("number='" + number + "'")
                .add("description='" + description + "'")
                .toString();
    }
}
