package ro.rasel.spring.microservices.ssoauthservice.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.stream.Stream;

@Component
@Order(Integer.MIN_VALUE)
public class ForwardPortHeaderFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(ForwardPortHeaderFilter.class);
    public static final String X_FORWARDED_HOST = "x-forwarded-host";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestUri = request.getRequestURI();
        if (Stream.of("/oauth/authorize", "/login").anyMatch(requestUri::endsWith)) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            filterChain.doFilter(servletRequest, new RedirectMissingPortHttpServletResponse(request, httpServletResponse));
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private static class RedirectMissingPortHttpServletResponse extends HttpServletResponseWrapper {
        private final HttpServletRequest request;

        public RedirectMissingPortHttpServletResponse(HttpServletRequest request, HttpServletResponse response) {
            super(response);
            this.request = request;
        }

        @Override
        public void sendRedirect(String location) throws IOException {
            String forwardHost = request.getHeader(X_FORWARDED_HOST);
            String requestUri = request.getRequestURI();
            LOG.info("Redirect location={} for request with requestUri={} and forwardedHost={}", location, requestUri, forwardHost);
            String newLocation = location;
            try {
                if (forwardHost != null) {
                    URI locationUri = new URI(location);
                    URI redirectUri = new URI("http://" + forwardHost);
                    int port = redirectUri.getPort();
                    if (locationUri.getPort() != port) {
                        newLocation = new URI(locationUri.getScheme(), locationUri.getUserInfo(), Optional.of(locationUri).map(URI::getHost).orElse("localhost"), port, locationUri.getPath(), locationUri.getQuery(), locationUri.getFragment()).toString();
                        LOG.info("replacing sendRedirect {} with {} ", locationUri, newLocation);
                    }
                }
            } catch (URISyntaxException e) {
                throw new IOException("Unable to process uri", e);
            }
            super.sendRedirect(newLocation);
        }
    }
}
