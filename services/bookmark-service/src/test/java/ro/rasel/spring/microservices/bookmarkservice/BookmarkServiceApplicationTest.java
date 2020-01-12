package ro.rasel.spring.microservices.bookmarkservice;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class BookmarkServiceApplicationTest {
    @SuppressWarnings("EmptyMethod")
    @Test
    void serverLoadProperly(){
    }
}