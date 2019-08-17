package ro.rasel.spring.microserivces.bookmarkservice;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class BookmarkServiceApplicationTest {
    @Test
    void serverLoadProperly(){
    }
}