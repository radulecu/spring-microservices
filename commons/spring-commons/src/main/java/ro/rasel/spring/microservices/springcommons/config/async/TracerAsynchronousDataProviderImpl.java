package ro.rasel.spring.microservices.springcommons.config.async;

import brave.Span;
import brave.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import ro.rasel.spring.microservices.commons.utils.async.AsynchronousDataProvider;
import ro.rasel.spring.microservices.commons.utils.async.ProviderName;

@Configuration
@ProviderName(SpringAsyncConfigurer.NAME)
public class TracerAsynchronousDataProviderImpl implements AsynchronousDataProvider<Span> {
    private final brave.Tracer tracer;

    public TracerAsynchronousDataProviderImpl(@Autowired(required = false) Tracer tracer) {
        this.tracer = tracer;
    }

    public Span extract() {
        return tracer != null ? tracer.currentSpan() : null;
    }

    @Override
    public boolean setup(Span span) {
        if (tracer != null) {
            tracer.withSpanInScope(tracer.newChild(span.context()));
            return true;
        }
        return false;
    }
}
