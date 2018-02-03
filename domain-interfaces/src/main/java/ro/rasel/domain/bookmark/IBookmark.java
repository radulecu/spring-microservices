package ro.rasel.domain.bookmark;

public interface IBookmark {
    Long getId();

    String getUserId();

    String getDescription();

    String getHref();

    String getLabel();
}
