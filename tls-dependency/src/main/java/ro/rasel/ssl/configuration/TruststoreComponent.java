package ro.rasel.ssl.configuration;

import org.springframework.context.annotation.ComponentScan;
import ro.rasel.spring.commons.utils.TempFileManager;

@ComponentScan(basePackageClasses = {TempFileManager.class, TruststoreConfig.class})
public class TruststoreComponent {
}
