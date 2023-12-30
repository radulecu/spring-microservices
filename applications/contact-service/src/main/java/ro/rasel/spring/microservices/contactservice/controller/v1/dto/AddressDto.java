package ro.rasel.spring.microservices.contactservice.controller.v1.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import java.beans.ConstructorProperties;
import java.util.Objects;
import java.util.StringJoiner;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

class AddressDto {
    private String country;
    private String town;
    private String street;
    private int number;
    private Integer entrance;
    private Integer flatNumber;

    @ConstructorProperties({"country", "town", "street", "number", "entrance", "flatNumber"})
    public AddressDto(
            String country, String town, String street, int number, Integer entrance, Integer flatNumber) {
        this.country = country;
        this.town = town;
        this.street = street;
        this.number = number;
        this.entrance = entrance;
        this.flatNumber = flatNumber;
    }

    AddressDto() {
    }

    @NotBlank(message = "Country should not be null or blank")
    @Schema(requiredMode = REQUIRED, example = "USA")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @NotBlank(message = "Town should not be null or blank")
    @Schema(requiredMode = REQUIRED, example = "New York")
    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @NotBlank(message = "Street should not be null or blank")
    @Schema(requiredMode = REQUIRED, example = "17th Street")
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Schema(example = "84")
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Schema(example = "3")
    public Integer getEntrance() {
        return entrance;
    }

    public void setEntrance(Integer entrance) {
        this.entrance = entrance;
    }

    @Schema(example = "123")
    public Integer getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(Integer flatNumber) {
        this.flatNumber = flatNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AddressDto that = (AddressDto) o;
        return number == that.number &&
                Objects.equals(country, that.country) &&
                Objects.equals(town, that.town) &&
                Objects.equals(street, that.street) &&
                Objects.equals(entrance, that.entrance) &&
                Objects.equals(flatNumber, that.flatNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, town, street, number, entrance, flatNumber);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AddressDto.class.getSimpleName() + "[", "]")
                .add("country='" + country + "'")
                .add("town='" + town + "'")
                .add("street='" + street + "'")
                .add("number=" + number)
                .add("entrance=" + entrance)
                .add("flatNumber=" + flatNumber)
                .toString();
    }
}
