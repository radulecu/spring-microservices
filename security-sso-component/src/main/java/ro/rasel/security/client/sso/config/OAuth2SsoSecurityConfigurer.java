package ro.rasel.security.client.sso.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@EnableOAuth2Sso
@Configuration
@Order(3)
public class OAuth2SsoSecurityConfigurer extends WebSecurityConfigurerAdapter {
    private final List<IWebSecurityConfigurer> configurers;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public OAuth2SsoSecurityConfigurer(@Autowired(required = false) List<IWebSecurityConfigurer> configurers) {
        configurers = configurers == null ? new ArrayList<>() : configurers;
        this.configurers = configurers;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
            .and()
                .authorizeRequests()
                .antMatchers("/user","/login").permitAll()
                .anyRequest().authenticated()
             .and()
                .headers()
                    .httpStrictTransportSecurity()
                    .includeSubDomains(true)
                    .maxAgeInSeconds(31536000)
                .and()
                    .httpPublicKeyPinning()
                        .addSha256Pins("iB3JG2L6auIhuHkRmF/Kb6NQ3qKMX13Bx/yjSDpqmBE=", "uAFOwqG0suu8SJHSt7xdLJSE4LIA0oFL8wOzk7Bk3EU=")
				        .reportOnly(false)
				        .includeSubDomains(true)
                .and()
                    .xssProtection()
                .and()
            .and()
                .rememberMe()
                .alwaysRemember(true)
            .and()
                .csrf()
                    .csrfTokenRepository(csrfTokenRepository())
            .and()
                .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class)
            .requestCache()
                .disable();

        // @formatter:on
        for (IWebSecurityConfigurer configurer : configurers) {
            configurer.configure(http);
        }
    }

    private Filter csrfHeaderFilter() {
        return new OncePerRequestFilter() {

            @Override
            protected void doFilterInternal(
                    HttpServletRequest request,
                    HttpServletResponse response, FilterChain filterChain)
                    throws ServletException, IOException {
                CsrfToken csrf = (CsrfToken) request
                        .getAttribute(CsrfToken.class.getName());
                if (csrf != null) {
                    Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
                    String token = csrf.getToken();
                    if (cookie == null
                            || token != null && !token.equals(cookie.getValue())) {
                        cookie = new Cookie("XSRF-TOKEN", token);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                    }
                }
                filterChain.doFilter(request, response);
            }

        };
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }
}