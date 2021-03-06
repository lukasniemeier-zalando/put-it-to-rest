package org.zalando.putittorest;

import org.springframework.http.client.AsyncClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.zalando.riptide.Rest;

import java.util.List;

final class RestFactory {

    public static Rest create(final AsyncClientHttpRequestFactory requestFactory, final List<HttpMessageConverter<?>> converters,
            final String baseUrl) {
        return Rest.builder()
                .requestFactory(requestFactory)
                .converters(converters)
                .baseUrl(baseUrl)
                .build();
    }

}
