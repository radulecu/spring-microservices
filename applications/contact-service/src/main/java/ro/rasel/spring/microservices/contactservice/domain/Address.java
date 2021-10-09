package ro.rasel.spring.microservices.contactservice.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
public class Address {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String country;

    @NotBlank
    private String town;

    @NotBlank
    private String street;

    private int number;

    private Integer entrance;

    private Integer flatNumber;

    public Address() {
    }

    public Address(
            Long id, Long contactId, String country, String town, String street, int number,
            Integer entrance, Integer flatNumber) {
        this(country, town, street, number, entrance, flatNumber);
        setId(id);
    }

    public Address(String country, String town, String street, int number, Integer entrance, Integer flatNumber) {
        setCountry(country);
        setTown(town);
        setStreet(street);
        setNumber(number);
        setEntrance(entrance);
        setFlatNumber(flatNumber);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Integer getEntrance() {
        return entrance;
    }

    public void setEntrance(Integer entrance) {
        this.entrance = entrance;
    }

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
        Address address = (Address) o;
        return number == address.number &&
                Objects.equals(id, address.id) &&
                Objects.equals(country, address.country) &&
                Objects.equals(town, address.town) &&
                Objects.equals(street, address.street) &&
                Objects.equals(entrance, address.entrance) &&
                Objects.equals(flatNumber, address.flatNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, country, town, street, number, entrance, flatNumber);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Address.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("country='" + country + "'")
                .add("town='" + town + "'")
                .add("street='" + street + "'")
                .add("number=" + number)
                .add("entrance=" + entrance)
                .add("flatNumber=" + flatNumber)
                .toString();
    }
}
