package ro.rasel.domain;

import ro.rasel.domain.bookmark.IBookmark;
import ro.rasel.domain.contact.IContact;

import java.util.Collection;

public interface IPassport {
    String getUserId();

    Collection<? extends IContact> getContacts();

    Collection<? extends IBookmark> getBookmarks();
}
