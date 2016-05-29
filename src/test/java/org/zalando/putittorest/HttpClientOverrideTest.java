package org.zalando.putittorest;

import org.apache.http.client.HttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.zalando.putittorest.Mocks.isMock;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration
public final class HttpClientOverrideTest {

    @Configuration
    @Import(DefaultTestConfiguration.class)
    public static class TestConfiguration {

        @Bean
        @Qualifier("example")
        public HttpClient exampleHttpClient() {
            return mock(HttpClient.class);
        }

    }

    @Autowired
    @Qualifier("example")
    private HttpClient unit;

    @Test
    public void shouldOverride() {
        assertThat(unit, isMock());
    }

}
