package ro.rasel.domain.contact;

public interface IContact {
    Long getId();

    String getRelationship();

    String getUserId();

    String getFirstName();

    String getLastName();

    String getEmail();
}
