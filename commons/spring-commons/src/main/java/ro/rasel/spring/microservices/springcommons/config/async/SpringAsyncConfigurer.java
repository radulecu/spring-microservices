package ro.rasel.spring.microservices.springcommons.config.async;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import ro.rasel.spring.microservices.commons.utils.Touple;
import ro.rasel.spring.microservices.commons.utils.async.AsynchronousDataProvider;
import ro.rasel.spring.microservices.commons.utils.async.ProviderName;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Configuration
public class SpringAsyncConfigurer implements AsyncConfigurer {
    public final static String NAME = "spring";

    private final List<AsynchronousDataProvider> asynchronousDataProviders;

    public SpringAsyncConfigurer(
            List<AsynchronousDataProvider> asynchronousDataProviders) {
        this.asynchronousDataProviders = asynchronousDataProviders != null ?
                asynchronousDataProviders.stream().filter(SpringAsyncConfigurer::isSpecific)
                        .collect(Collectors.toList()) :
                Collections.emptyList();
    }

    private static boolean isSpecific(AsynchronousDataProvider asynchronousDataProvider) {
        final ProviderName providerName = AnnotationUtils
                .findAnnotation(asynchronousDataProvider.getClass(), ProviderName.class);
        final List<String> names = providerName != null ? Arrays.asList(providerName.value()) :
                Collections.singletonList(ProviderName.ALL);
        return names.contains("all") || names.contains(NAME);
    }

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("threadAsync");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setTaskDecorator(new AsyncTaskDecorator());
        executor.initialize();
        return executor;
    }

    private class AsyncTaskDecorator implements TaskDecorator {
        @Override
        public Runnable decorate(Runnable runnable) {
            final AtomicReference<Map<Class<? extends AsynchronousDataProvider>, Object>> mapAtomicReference =
                    extractContext();
            return () -> {
                final List<? extends Class<? extends AsynchronousDataProvider>> toClean =
                        setupContext(mapAtomicReference);
                runnable.run();
                cleanupContext(toClean);
            };
        }
    }

    private AtomicReference<Map<Class<? extends AsynchronousDataProvider>, Object>> extractContext() {
        AtomicReference<Map<Class<? extends AsynchronousDataProvider>, Object>> contextHolder =
                new AtomicReference<>();
        Map<Class<? extends AsynchronousDataProvider>, Object> context = asynchronousDataProviders.stream()
                .map(asynchronousDataProvider -> new Touple<>(asynchronousDataProvider.getClass(),
                        asynchronousDataProvider.extract()))
                .filter(classObjectTouple -> classObjectTouple.getQ() != null)
                .collect(Collectors.toMap(Touple::getP, Touple::getQ));
        contextHolder.set(context);
        return contextHolder;
    }

    private List<? extends Class<? extends AsynchronousDataProvider>> setupContext(
            AtomicReference<Map<Class<? extends AsynchronousDataProvider>, Object>> contextHolder) {
        final Map<Class<? extends AsynchronousDataProvider>, Object> context = contextHolder.get();
        if (context != null) {
            //noinspection unchecked
            return asynchronousDataProviders.stream()
                    .map(asynchronousDataProvider -> new Touple<>(asynchronousDataProvider,
                            context.get(asynchronousDataProvider.getClass())))
                    .filter(touple -> touple.getQ() != null)
                    .map(touple -> new Touple<>(touple.getP().getClass(), touple.getP().setup(touple.getQ())))
                    .filter(touple -> touple.getQ())//leave only touples that should not be cleaned
                    .map(Touple::getP)
                    .collect(Collectors.toList());
        }
        return null;
    }

    private void cleanupContext(List<? extends Class<? extends AsynchronousDataProvider>> toClean) {
        asynchronousDataProviders.stream()
                .filter(toClean::contains)
                .forEach(AsynchronousDataProvider::clean);
    }
}
