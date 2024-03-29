package ro.rasel.spring.microservices.component.securityclient.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;
import ro.rasel.spring.microservices.component.securityclient.web.config.properties.WebSecurityProperties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Configuration
@Order(2)
public class OAuth2SsoSecurityConfigurer extends WebSecurityConfigurerAdapter {
    private final List<IWebSecurityConfigurer> configurers;
    private final WebSecurityProperties webSecurityProperties;

    public OAuth2SsoSecurityConfigurer(
            @Autowired(required = false) List<IWebSecurityConfigurer> configurers,
            WebSecurityProperties webSecurityProperties) {
        this.webSecurityProperties = webSecurityProperties;
        configurers = configurers == null ? new ArrayList<>() : configurers;
        this.configurers = configurers;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .oauth2Login()
            .and()
            .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
            .and()
            .antMatcher(webSecurityProperties.getUrlAntMatcher())
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


        if (configurers.size()==0){
            http.authorizeRequests().anyRequest().authenticated();
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