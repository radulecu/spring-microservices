package ro.rasel.spring.microservices.component.securityclient.web;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(WebSecurityClientComponent.class)
public @interface EnableWebSecurityClientComponent {
}
