package ro.rasel.spring.microserivces.service.zipkin;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import zipkin2.server.internal.EnableZipkinServer;
import zipkin2.server.internal.ZipkinActuatorImporter;
import zipkin2.server.internal.ZipkinModuleImporter;
import zipkin2.server.internal.banner.ZipkinBanner;

@EnableZipkinServer
@SpringBootApplication
public class ZipkinDashboardServiceApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ZipkinDashboardServiceApplication.class)
                .banner(new ZipkinBanner())
                .initializers(new ZipkinModuleImporter(), new ZipkinActuatorImporter())
                .properties(
                        EnableAutoConfiguration.ENABLED_OVERRIDE_PROPERTY + "=false",
                        "spring.config.name=zipkin-server").run(args);
    }


}
