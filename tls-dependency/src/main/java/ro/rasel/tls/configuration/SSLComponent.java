package ro.rasel.tls.configuration;

import org.springframework.context.annotation.ComponentScan;
import ro.rasel.spring.commons.utils.TempFileManager;

@ComponentScan(basePackageClasses = {TempFileManager.class, SSLConfig.class})
public class SSLComponent {
}
