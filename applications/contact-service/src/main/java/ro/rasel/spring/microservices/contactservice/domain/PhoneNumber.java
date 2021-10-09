package ro.rasel.spring.microservices.contactservice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
public class PhoneNumber {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String number;

    @Size(min = 1)
    private String description;

    public PhoneNumber() {
    }

    public PhoneNumber(Long id, String number, String description) {
        this(number, description);
        setId(id);
    }

    public PhoneNumber(String number, String description) {
        setNumber(number);
        setDescription(description);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

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
        PhoneNumber that = (PhoneNumber) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(number, that.number) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, description);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PhoneNumber.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("number='" + number + "'")
                .add("description='" + description + "'")
                .toString();
    }
}
