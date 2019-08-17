package ro.rasel.spring.microserivces.bookmarkservice.utils;

public class TestConstants {
    public static final String USER_ID = "userId";
    public static final String HREF = String.format("http://some-other-host-for-%s.com", USER_ID);
    public static final String DESCRIPTION = String.format("A description for %s's link", USER_ID);
    public static final String LABEL = String.format("%sLabel", USER_ID);
    public static final int BOOKMARK_ID = 1;
}
