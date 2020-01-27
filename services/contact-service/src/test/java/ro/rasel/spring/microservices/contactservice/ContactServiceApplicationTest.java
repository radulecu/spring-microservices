package ro.rasel.spring.microservices.contactservice;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ContactServiceApplicationTest {
    @SuppressWarnings("EmptyMethod")
    @Test
    void serverLoadProperly() {
    }
}