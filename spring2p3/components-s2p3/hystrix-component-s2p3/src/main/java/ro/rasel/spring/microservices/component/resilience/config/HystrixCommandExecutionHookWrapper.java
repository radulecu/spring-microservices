package ro.rasel.spring.microservices.component.resilience.config;

import com.netflix.hystrix.HystrixInvokable;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;
import ro.rasel.spring.microservices.common.utils.Touple;
import ro.rasel.spring.microservices.common.utils.async.AsynchronousDataProvider;
import ro.rasel.spring.microservices.common.utils.async.AsynchronousDataProviderHelper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HystrixCommandExecutionHookWrapper extends HystrixCommandExecutionHook {
    public static final String NAME = "hystrix";
    private final HystrixCommandExecutionHook hystrixCommandExecutionHook;
    private final List<AsynchronousDataProvider> asynchronousDataProviders;
    private HystrixRequestVariableDefault<Map<Class<? extends AsynchronousDataProvider>, Object>>
            hystrixStorage = new HystrixRequestVariableDefault<>();

    public HystrixCommandExecutionHookWrapper(
            HystrixCommandExecutionHook hystrixCommandExecutionHook,
            List<AsynchronousDataProvider> asynchronousDataProviders) {
        this.hystrixCommandExecutionHook = hystrixCommandExecutionHook;
        this.asynchronousDataProviders =
                AsynchronousDataProviderHelper.filterAsynchronousDataProviders(asynchronousDataProviders, NAME);
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
                .filter(classObjectTouple -> classObjectTouple.getQ() != null)
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
            asynchronousDataProviders.stream()
                    .map(asynchronousDataProvider -> new Touple<>(asynchronousDataProvider,
                            hystrixData.get(asynchronousDataProvider.getClass())))
                    .filter(touple -> touple.getQ() != null)
                    .map(touple -> new Touple<>(touple.getP().getClass(), touple.getP().setup(touple.getQ())))
                    .filter(touple -> !touple.getQ())//leave only touples that should not be cleaned
                    .map(Touple::getP)
                    .forEach(hystrixData::remove);
            hystrixStorage.set(hystrixData);
        }
    }

    private void cleanup() {
        final Map<Class<? extends AsynchronousDataProvider>, Object> hystrixData = hystrixStorage.get();
        if (hystrixData != null) {
            asynchronousDataProviders.stream()
                    .map(asynchronousDataProvider -> new Touple<>(asynchronousDataProvider,
                            hystrixData.get(asynchronousDataProvider.getClass())))
                    .filter(touple -> touple.getQ() != null)
                    .map(Touple::getP)
                    .forEach(AsynchronousDataProvider::clean);

        }
        HystrixRequestContext.getContextForCurrentThread().shutdown();
    }
}