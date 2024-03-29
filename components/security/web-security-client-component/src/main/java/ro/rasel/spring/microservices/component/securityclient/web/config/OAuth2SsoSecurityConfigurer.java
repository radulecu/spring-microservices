package ro.rasel.spring.microservices.component.securityclient.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;
import ro.rasel.spring.microservices.component.securityclient.web.config.properties.WebSecurityProperties;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Configuration
public class OAuth2SsoSecurityConfigurer {
    private final List<IWebSecurityConfigurer> configurers;
    private final WebSecurityProperties webSecurityProperties;

    public OAuth2SsoSecurityConfigurer(
            @Autowired(required = false) List<IWebSecurityConfigurer> configurers,
            WebSecurityProperties webSecurityProperties) {
        this.webSecurityProperties = webSecurityProperties;
        configurers = configurers == null ? new ArrayList<>() : configurers;
        this.configurers = configurers;
    }

    @Bean
    @Order(2)
    public SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        HttpSecurity httpSecurity = http
            .oauth2Login()
            .and()
            .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
            .and()
            .securityMatcher(webSecurityProperties.getUrlAntMatcher())
            .authorizeRequests((ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry auth) -> {
                    for (IWebSecurityConfigurer configurer : configurers) {
                        configurer.getExpressionInterceptUrlRegistryCustomizer().customize(auth);
                    }
                })
            .headers()
                    .httpStrictTransportSecurity()
                    .includeSubDomains(true)
                    .maxAgeInSeconds(31536000)
                .and()
                    .httpPublicKeyPinning()
                    .addSha256Pins("1YVSjz202T4i0VlJ7Shv2NqhFSpNaadt8eqNsjHsC0w=")
                    .reportOnly(false)
                    .includeSubDomains(false)
                .and()
                    .contentSecurityPolicy("script-src 'self'")
                .and()
                    .xssProtection()
                .and()
                    .contentTypeOptions()
                .and()
                    .cacheControl()
                .and()
                    .frameOptions()
                .and()
            .and()
                .csrf()
                    .csrfTokenRepository(csrfTokenRepository())
            .and()
                .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);

        // @formatter:on


        if (configurers.isEmpty()){
            httpSecurity = httpSecurity.authorizeRequests().anyRequest().authenticated().and();
        }

        return httpSecurity.build();
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