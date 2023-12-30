package ro.rasel.spring.microservices.component.swagger.config.properties;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties("server")
public class SwaggerConfigProperties {
    @NestedConfigurationProperty
    private Info info;

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    //    private boolean enabled = false;

//    private Set<String> blacklist = new HashSet<>();

//    public boolean isEnabled() {
//        return enabled;
//    }

//    public void setEnabled(boolean enabled) {
//        this.enabled = enabled;
//    }

//    public Set<String> getBlacklist() {
//        return blacklist;
//    }

    //    public void setBlacklist(Set<String> blacklist) {
//        this.blacklist = blacklist;
//    }
    public static class Info {
        private String title;
        private String summary;
        private String description;
        private String version;
        private String termsOfService;
        private Map<String, Object> extensions;
        @NestedConfigurationProperty
        private Contact contact;
        @NestedConfigurationProperty
        private License license;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getTermsOfService() {
            return termsOfService;
        }

        public void setTermsOfService(String termsOfService) {
            this.termsOfService = termsOfService;
        }

        public Map<String, Object> getExtensions() {
            return extensions;
        }

        public void setExtensions(Map<String, Object> extensions) {
            this.extensions = extensions;
        }

        public Contact getContact() {
            return contact;
        }

        public void setContact(Contact contact) {
            this.contact = contact;
        }

        public License getLicense() {
            return license;
        }

        public void setLicense(License license) {
            this.license = license;
        }

        public io.swagger.v3.oas.models.info.Info toSwaggerInfo() {
            return new io.swagger.v3.oas.models.info.Info()
                    .title(title)
                    .summary(summary)
                    .description(description)
                    .version(version)
                    .termsOfService(termsOfService)
                    .extensions(extensions)
                    .contact(contact)
                    .license(license);
        }

    }
}
