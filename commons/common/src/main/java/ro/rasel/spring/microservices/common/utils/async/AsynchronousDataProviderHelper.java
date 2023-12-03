package ro.rasel.spring.microservices.common.utils.async;

import org.apache.commons.lang3.AnnotationUtils;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("rawtypes")
public class AsynchronousDataProviderHelper {
    public static List<AsynchronousDataProvider> filterAsynchronousDataProviders(
            List<AsynchronousDataProvider> asynchronousDataProviders, String name) {
        return asynchronousDataProviders != null ?
                asynchronousDataProviders.stream()
                        .filter(asynchronousDataProvider -> shouldProcess(name, asynchronousDataProvider.getClass()))
                        .collect(Collectors.toList()) :
                Collections.emptyList();
    }

    private static boolean shouldProcess(String name,
                                         Class<? extends AsynchronousDataProvider> asynchronousDataProviderClass) {
        final List<ProviderName> providerNames =
                Arrays.asList(asynchronousDataProviderClass.getAnnotationsByType(ProviderName.class));
        final Set<String> names = providerNames
                .stream()
                .map(ProviderName::value)
                .map(Arrays::asList)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        return names.isEmpty() || names.contains(ProviderName.ALL) || names.contains(name);
    }
}
