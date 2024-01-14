package ro.rasel.spring.microservices.resourcegatewayservice.filter;

import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;

@Component
public class SwaggerV3GatewayFilterFactory extends
        AbstractGatewayFilterFactory<Void> {

    public SwaggerV3GatewayFilterFactory() {
        super(Void.class);
    }

    @Override
    public GatewayFilter apply(Void config) {
        return new SwaggerV3GatewayFilter();
    }

    public static class SwaggerV3GatewayFilter implements GatewayFilter, Ordered {

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            String path = exchange.getRequest().getURI().getPath();
            if (path.endsWith("/swagger-ui/swagger-initializer.js") ||
                    path.endsWith("/v3/api-docs/swagger-config")) {
                SwaggerV3GatewayFilter.BodyProcessor bodyProcessor =
                        (originalBody, prefix, url) -> originalBody
                                .replaceAll("/v3/api-docs", prefix + "/v3/api-docs")
                                .replaceAll("(\"oauth2RedirectUrl\" ?: ?\").*/swagger-ui/oauth2-redirect.html",
                                        "$1" + url + prefix + "/swagger-ui/oauth2-redirect.html");
                return chain.filter(exchange.mutate().response(decorate(exchange, bodyProcessor)).build());
            } else if (path.endsWith("/v3/api-docs")) {
                SwaggerV3GatewayFilter.BodyProcessor bodyProcessor =
                        (originalBody, prefix, url) -> originalBody
                                .replaceAll("(\"url\" ?: ?)\".*\"", "$1\"" + url + prefix + "\"");
                return chain.filter(exchange.mutate().response(decorate(exchange, bodyProcessor)).build());
            } else {
                return chain.filter(exchange);
            }
        }

        ServerHttpResponse decorate(ServerWebExchange exchange,
                                    SwaggerV3GatewayFilter.BodyProcessor bodyProcessor) {
            return new ServerHttpResponseDecorator(exchange.getResponse()) {

                @Override
                public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {

                    ClientResponse clientResponse = ClientResponse
                            .create(exchange.getResponse().getStatusCode())
                            .body(Flux.from(body))
                            .build();

                    String prefix = exchange.getRequest().getPath().toString()
                            .replace("/swagger-ui/swagger-initializer.js", "")
                            .replace("/v3/api-docs/swagger-config", "")
                            .replace("/v3/api-docs", "");
                    String url = MessageFormat.format("{0}://{1}:{2}", exchange.getRequest().getURI().getScheme(),
                            exchange.getRequest().getURI().getHost(),
                            String.valueOf(exchange.getRequest().getURI().getPort()));

                    Mono<String> modifiedBody = clientResponse.bodyToMono(String.class)
                            .flatMap(originalBody -> Mono.just(bodyProcessor.process(originalBody, prefix, url)));

                    BodyInserter<Mono<String>, ReactiveHttpOutputMessage> bodyInserter =
                            BodyInserters.fromPublisher(modifiedBody, String.class);
                    CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(
                            exchange, exchange.getResponse().getHeaders());
                    return bodyInserter.insert(outputMessage, new BodyInserterContext())
                            .then(Mono.defer(() -> {
                                Flux<DataBuffer> messageBody = outputMessage.getBody();
                                HttpHeaders headers = getDelegate().getHeaders();
                                if (!headers.containsKey(HttpHeaders.TRANSFER_ENCODING)) {
                                    messageBody = messageBody.doOnNext(data -> headers
                                            .setContentLength(data.readableByteCount()));
                                }
                                return getDelegate().writeWith(messageBody);
                            }));
                }

                @Override
                public Mono<Void> writeAndFlushWith(
                        Publisher<? extends Publisher<? extends DataBuffer>> body) {
                    return writeWith(Flux.from(body).flatMapSequential(p -> p));
                }

            };
        }

        @Override
        public int getOrder() {
            return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 1;
        }

        private interface BodyProcessor {
            String process(String originalBody, String prefix, String url);
        }
    }
}
