package ro.rasel.spring.microservices.ssoauthservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"ssl.enabled=false", "server.ssl.client-auth="})
public class AuthorizationTokenGrantTypeV2Test {
    private static final String USER = "jlong";
    private static final String SECRET = "spring";
    private static final String CLIENT_ID = "acme";
    private static final String CLIENT_SECRET = "acmesecret";
    private static final String REDIRECT_URL = "https://localhost:8082/webjars/springfox-swagger-ui/oauth2-redirect.html";

    private static final String LOGIN_URL = "/login?username={username}&password={password}";
    private static final String GET_AUTHORIZATION_CODE_URL =
            "/oauth/authorize?" +
                    "client_id={clientId}" +
                    "&redirect_uri={redirectUri}" +
                    "&response_type=code" +
                    "&scope=read write" +
                    "&steate=U2F0IEphbiAwNiAyMDI0IDIxOjU4OjM4IEdNVCswMjAwIChFYXN0ZXJuIEV1cm9wZWFuIFN0YW5kYXJkIFRpbWUp";
    private static final String AUTHORIZATION_TOKEN_URL =
            "/oauth/token?client_id={client_id}&redirect_uri={redirectUri}&grant_type=authorization_code&code={code}";
    private static final String USER_URL = "/user";

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    private ServletWebServerApplicationContext servletWebServerApplicationContext;

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
        final String sessionId= getAuthorizationCodeInitialRequest();
        final String cookieValue = postLogin(username, password, sessionId);
        final String code = getAuthorizationCode(cookieValue);
        return getAuthorizationToken(code);
    }

    private String getAuthorizationCodeInitialRequest() {
        // curl -k -v -X GET "https://localhost:9999/sso/oauth/authorize?client_id=acme&redirect_uri=http://localhost:8082/ui/login&response_type=code&state=KSnb6S" -H "Cookie: JSESSIONID=02B70F057CB0C960F3417DEAEF528E4D"
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Accept","/");
        final ResponseEntity<Void> authorizeResponseEntity =
                testRestTemplate
                        .exchange(GET_AUTHORIZATION_CODE_URL,
                                HttpMethod.GET,
                                new HttpEntity<>(headers), Void.class, CLIENT_ID, REDIRECT_URL);
        assertEquals(HttpStatus.FOUND, authorizeResponseEntity.getStatusCode());

        String jsessionid = getCookies(authorizeResponseEntity).get("JSESSIONID");
        String location = authorizeResponseEntity.getHeaders().get("Location").get(0);
        assertThat(location, is("http://localhost:"+servletWebServerApplicationContext.getWebServer().getPort()+"/sso/login"));

        getLogin(location, jsessionid);

        return jsessionid;
    }

    private void getLogin(String url, String sessionId) {
        // curl -k -v -X GET https://localhost:9999/sso/login -H "Content-Type: application/x-www-form-urlencoded" -d "username=jlong" -d "password=spring"
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "JSESSIONID=" + sessionId);
        final ResponseEntity<String> loginResponseEntity =
                testRestTemplate
                        .getForEntity(url, String.class, Void.class);
        assertEquals(HttpStatus.OK, loginResponseEntity.getStatusCode());
        assertThat(loginResponseEntity.getHeaders().get("Content-Type").get(0), is("text/html;charset=UTF-8"));
    }

    private String postLogin(String username, String password, String sessionId) {
        // curl -k -v -X POST https://localhost:9999/sso/login -H "Content-Type: application/x-www-form-urlencoded" -d "username=jlong" -d "password=spring"
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "JSESSIONID=" + sessionId);
        final ResponseEntity<Void> loginResponseEntity =
                testRestTemplate
                        .postForEntity(LOGIN_URL, null, Void.class, username,
                                password);
        assertEquals(HttpStatus.FOUND, loginResponseEntity.getStatusCode());
        assertEquals(Arrays.asList("http://localhost:"+servletWebServerApplicationContext.getWebServer().getPort()+"/sso/"), loginResponseEntity.getHeaders().get("Location"));
//        assertThat(loginResponseEntity.getHeaders().get("Location").get(0), is(GET_AUTHORIZATION_CODE_URL)); TODO: check why redirect does not work correctly
        return ((Map<String, String>) getCookies(loginResponseEntity)).get("JSESSIONID");
    }

    private String getAuthorizationCode(String sessionId) {
        // curl -k -v -X GET "https://localhost:9999/sso/oauth/authorize?client_id=acme&redirect_uri=http://localhost:8082/ui/login&response_type=code&state=KSnb6S" -H "Cookie: JSESSIONID=02B70F057CB0C960F3417DEAEF528E4D"
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "JSESSIONID=" + sessionId);
        final ResponseEntity<Void> authorizeResponseEntity =
                testRestTemplate
                        .exchange(GET_AUTHORIZATION_CODE_URL,
                                HttpMethod.POST,
                                new HttpEntity<>(headers), Void.class, CLIENT_ID, REDIRECT_URL);
        assertEquals(HttpStatus.SEE_OTHER, authorizeResponseEntity.getStatusCode());
        final String location = authorizeResponseEntity.getHeaders().get(HttpHeaders.LOCATION).get(0);
        return location.substring(location.indexOf("code=") + 5);
    }

    private String getAuthorizationToken(String code) {
        // curl -k -v -X POST -u acme:acmesecret "https://localhost:9999/sso/oauth/token?client_id=acme&redirect_uri=http://localhost:8082/ui/login&grant_type=authorization_code&code=85k67t"
        final ResponseEntity<AuthorizationTokenResponse> responseEntity =
                testRestTemplate.withBasicAuth(CLIENT_ID, CLIENT_SECRET).postForEntity(
                        AUTHORIZATION_TOKEN_URL,
                        null, AuthorizationTokenResponse.class, CLIENT_ID, REDIRECT_URL, code);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
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
