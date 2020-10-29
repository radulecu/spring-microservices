package ro.rasel.spring.microservices.commons.utils.future;

import rx.Single;

import java.util.concurrent.CompletableFuture;

public class RxFutureConverterUtils {
    private RxFutureConverterUtils() {
        throw new IllegalStateException("utility class, should not be instantiated");
    }

    public static <T> CompletableFuture<T> toCompletableFuture(Single<T> single) {
        CompletableFuture<T> completableFuture = new CompletableFuture<>();
        single.subscribe(completableFuture::complete, completableFuture::completeExceptionally);
        return completableFuture;
    }
}
