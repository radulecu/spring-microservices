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
import ro.rasel.ssl.truststore.EnableTruststoreComponent;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"ssl.certificate.name=localhost","spring.profiles.include=sslTrustStore"})
@EnableTruststoreComponent
public class UserInfoEndpointLiveTest {

    public static final String USER = "jlong";
    public static final String SECRET = "spring";
    public static final String CLIENT_ID = "acme";
    public static final String CLIENT_SECRET = "acmesecret";
    public static final String REDIRECT_URL = "http://localhost:8082/ui/login";

    public static final String LOGIN_URL = "/login?username={username}&password={password}";
    public static final String AUTHORIZATION_TOKEN_URL =
            "/oauth/token?client_id={client_id}&redirect_uri={redirectUri}&grant_type=authorization_code&code={code}";
    public static final String USER_URL = "/user";
    public static final String GET_AUTHORIZATION_CODE_URL =
            "/oauth/authorize?client_id={clientId}&redirect_uri={redirectUri}&response_type=code";

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

    private String getAuthorizationToken(String username, String password) {
        final String cookieValue = login(username, password);
        final String code = getAuthorizationCode(cookieValue);
        return getAuthorizationToken(code);
    }

    private String login(String username, String password) {
        // curl -v -X POST http://localhost:9999/login -H "Cookie: JSESSIONID=FA77B9C7A7BDB71B40C872BCE06CD11E" -H "Content-Type: application/x-www-form-urlencoded" -d "username=jlong" -d "password=spring"
        final ResponseEntity<Void> loginResponseEntity =
                testRestTemplate
                        .postForEntity(LOGIN_URL, null, Void.class, username,
                                password);
        assertEquals(HttpStatus.FOUND, loginResponseEntity.getStatusCode());
        return ((Map<String, String>) getCookies(loginResponseEntity)).get("JSESSIONID");
    }

    private String getAuthorizationCode(String cookieValue) {
        // curl -v -X GET "http://localhost:9999/oauth/authorize?client_id=acme&redirect_uri=http://localhost:8082/ui/login&response_type=code&state=KSnb6S" -H "Cookie: JSESSIONID=02B70F057CB0C960F3417DEAEF528E4D"
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "JSESSIONID=" + cookieValue);
        final ResponseEntity<Void> authorizeResponse =
                testRestTemplate
                        .exchange(GET_AUTHORIZATION_CODE_URL,
                                HttpMethod.POST,
                                new HttpEntity<>(headers), Void.class, CLIENT_ID, REDIRECT_URL);
        assertEquals(HttpStatus.FOUND, authorizeResponse.getStatusCode());
        final String location = authorizeResponse.getHeaders().get(HttpHeaders.LOCATION).get(0);
        return location.substring(location.indexOf("code=") + 5);
    }

    private String getAuthorizationToken(String code) {
        // curl -v -X POST -u acme:acmesecret "http://localhost:9999/oauth/token?client_id=acme&redirect_uri=http://localhost:8082/ui/login&grant_type=authorization_code&code=R5S0kI"
        final ResponseEntity<AuthorizationTokenResponse> responseEntity =
                testRestTemplate.withBasicAuth(CLIENT_ID, CLIENT_SECRET).postForEntity(
                        AUTHORIZATION_TOKEN_URL,
                        null, AuthorizationTokenResponse.class, CLIENT_ID, REDIRECT_URL, code);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        //noinspection unchecked
        return responseEntity.getBody().getAccessToken();
    }

    private HashMap<String, String> getCookies(ResponseEntity<Void> stringResponseEntity) {
        return Arrays.stream(stringResponseEntity.getHeaders().get("Set-Cookie").get(0).split(";"))
                .map(s -> s.split("=")).collect(HashMap::new,
                        (m, split) -> m
                                .put(split[0], Optional.ofNullable(split).filter(s -> s.length > 1).map(s -> s[1])
                                        .orElse(null)), HashMap::putAll);
    }

    private static class AuthorizationTokenResponse {
        private String accessToken;

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
