package ro.rasel.spring.microservices.common.utils.future;

import org.hamcrest.core.*;
import org.junit.jupiter.api.*;
import rx.Single;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;
import static org.junit.jupiter.api.Assertions.*;

class RxFutureConverterUtilsTest {
    @Test
    public void shouldReturnCompletableFutureWithValueWhenCreatingSingleValue()
            throws ExecutionException, InterruptedException {
        Single<?> single = Single.just(100);

        final CompletableFuture<?> completableFuture = RxFutureConverterUtils.toCompletableFuture(single);

        assertThat(completableFuture.get(), is(100));
    }

    @Test
    public void shouldThrowErrorWhenCreatingSingleError() {
        Single<?> single = Single.error(new RuntimeException("re test"));

        final CompletableFuture<?> completableFuture = RxFutureConverterUtils.toCompletableFuture(single);

        final ExecutionException exception = assertThrows(ExecutionException.class, completableFuture::get);
        assertThat(exception.getCause().getClass(), Is.is(RuntimeException.class));
    }

}