package ro.rasel.spring.microservices.component.hystrix.config;

import com.netflix.hystrix.HystrixInvokable;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;
import ro.rasel.spring.microserivces.commons.utils.Touple;
import ro.rasel.spring.microserivces.commons.utils.async.AsynchronousDataProvider;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HystrixCommandExecutionHookWrapper extends HystrixCommandExecutionHook {
    private final HystrixCommandExecutionHook hystrixCommandExecutionHook;
    private final List<AsynchronousDataProvider> asynchronousDataProviders;
    private HystrixRequestVariableDefault<Map<Class<? extends AsynchronousDataProvider>, Object>>
            hystrixStorage = new HystrixRequestVariableDefault<>();

    public HystrixCommandExecutionHookWrapper(
            HystrixCommandExecutionHook hystrixCommandExecutionHook,
            List<AsynchronousDataProvider> asynchronousDataProviders) {
        this.hystrixCommandExecutionHook = hystrixCommandExecutionHook;
        this.asynchronousDataProviders = asynchronousDataProviders;
    }

    public <T> void onStart(HystrixInvokable<T> commandInstance) {
        hystrixCommandExecutionHook.onStart(commandInstance);
        extractContext();
    }

    public <T> void onThreadStart(HystrixInvokable<T> commandInstance) {
        hystrixCommandExecutionHook.onThreadStart(commandInstance);
        setupContext();
    }

    public <T> void onFallbackStart(HystrixInvokable<T> commandInstance) {
        hystrixCommandExecutionHook.onFallbackStart(commandInstance);
        setupContext();
    }

    public <T> Exception onFallbackError(HystrixInvokable<T> commandInstance, Exception e) {
        final Exception exception = hystrixCommandExecutionHook.onFallbackError(commandInstance, e);
        cleanup();
        return exception;
    }

    public <T> void onFallbackSuccess(HystrixInvokable<T> commandInstance) {
        hystrixCommandExecutionHook.onFallbackSuccess(commandInstance);
        cleanup();
    }

    public <T> Exception onExecutionError(HystrixInvokable<T> commandInstance, Exception e) {
        final Exception exception = hystrixCommandExecutionHook.onExecutionError(commandInstance, e);
        cleanup();
        return exception;
    }

    public <T> void onExecutionSuccess(HystrixInvokable<T> commandInstance) {
        hystrixCommandExecutionHook.onExecutionSuccess(commandInstance);
        cleanup();
    }

    private void extractContext() {
        HystrixRequestContext.initializeContext();
        Map<Class<? extends AsynchronousDataProvider>, Object> context = asynchronousDataProviders.stream()
                .map(asynchronousDataProvider -> new Touple<>(asynchronousDataProvider.getClass(),
                        asynchronousDataProvider.extract()))
                .collect(Collectors.toMap(Touple::getP, Touple::getQ));
        if (!context.isEmpty()) {
            hystrixStorage.set(context);
        }
    }

    private void setupContext() {
        if (HystrixRequestContext.getContextForCurrentThread() == null) {
            extractContext();
        }
        final Map<Class<? extends AsynchronousDataProvider>, Object> hystrixData = hystrixStorage.get();
        if (hystrixData != null) {
            //noinspection unchecked
            asynchronousDataProviders.forEach(asynchronousDataProvider -> asynchronousDataProvider.setup(hystrixData.get(asynchronousDataProvider.getClass())));
        }
    }

    private void cleanup() {
        HystrixRequestContext.getContextForCurrentThread().shutdown();
    }
}