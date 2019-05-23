package ro.rasel.server.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ro.rasel.ssl.client.EnableSslClientComponent;

import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"ssl.certificate.name=localhost"})
@EnableSslClientComponent
public class PasswordGrantTypeTest {

    private static final String USER = "jlong";
    private static final String SECRET = "spring";
    private static final String CLIENT_ID = "acme";
    private static final String CLIENT_SECRET = "acmesecret";
    private static final String REDIRECT_URL = "http://localhost:8082/login";

    private static final String AUTHORIZATION_TOKEN_URL =
            "/oauth/token?client_id={clientId}&client_secret={clientSecret}&grant_type=password&username={user}&password={password}";
    private static final String USER_URL = "/user";

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void givenAccessTokenWhenAccessUserInfoEndpointThenSuccess() {
        String accessToken = getAuthorizationToken(USER, SECRET);

        final HttpHeaders headers = new HttpHeaders();
        headers.put(HttpHeaders.AUTHORIZATION, Collections.singletonList("Bearer " + accessToken));
        final ResponseEntity<Map> responseEntity =
                testRestTemplate.exchange(USER_URL, HttpMethod.GET, new HttpEntity<>(headers), Map.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(USER, responseEntity.getBody().get("name"));
    }

    private String getAuthorizationToken(String user, String secret) {
        // curl -k -X POST -vu acme:acmesecret https://localhost:9999/sso/oauth/token -H "Accept: application/json" -d "client_id=acme&client_secret=acmesecret&grant_type=password&username=jlong&password=spring"
        final ResponseEntity<AuthorizationTokenResponse> responseEntity =
                testRestTemplate.withBasicAuth(CLIENT_ID, CLIENT_SECRET).postForEntity(
                        AUTHORIZATION_TOKEN_URL,
                        null, AuthorizationTokenResponse.class, CLIENT_ID, CLIENT_SECRET, user, secret);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        return responseEntity.getBody().getAccessToken();
    }

        private static class AuthorizationTokenResponse {
        private final String accessToken;

        private AuthorizationTokenResponse(@JsonProperty("access_token") String accessToken) {
            this.accessToken = accessToken;
        }

        public String getAccessToken() {
            return accessToken;
        }

        @Override
        public String toString() {
            return "AuthorizationTokenResponse{" +
                    "accessToken='" + accessToken + '\'' +
                    '}';
        }
    }

}
