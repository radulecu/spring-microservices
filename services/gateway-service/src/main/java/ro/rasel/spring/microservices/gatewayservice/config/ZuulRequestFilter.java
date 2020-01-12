package ro.rasel.spring.microservices.gatewayservice.config;

import brave.Tracer;
import brave.propagation.TraceContext;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

@Component
public class ZuulRequestFilter extends ZuulFilter {
    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER = true;
    private final Tracer tracer;

    public ZuulRequestFilter(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        addTracingHeaders(ctx);
        return null;
    }

    private void addTracingHeaders(RequestContext ctx) {
        final TraceContext parentContext = tracer.currentSpan().context();
        final TraceContext context = tracer.newChild(parentContext).context();
        final String traceId = context.traceIdString();
        final String spanId = context.spanIdString();
        final String parentSpanId = context.parentIdString();
        if (traceId != null) {
            ctx.addZuulRequestHeader("X-B3-TraceId", traceId);
        }
        if (spanId != null) {
            ctx.addZuulRequestHeader("X-B3-SpanId", spanId);
        }
        if (parentSpanId != null) {
            ctx.addZuulRequestHeader("X-B3-ParentSpanId", parentSpanId);
        }
        ctx.addZuulRequestHeader("X-B3-Sampled", context.sampled() ? "1" : "0");
    }
}
