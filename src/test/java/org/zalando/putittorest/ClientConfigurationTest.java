package org.zalando.putittorest;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.Configurable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;
import org.zalando.riptide.Rest;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DefaultTestConfiguration.class)
@TestPropertySource(properties = {
    "rest.clients.example.timeouts.connect: 12",
    "rest.clients.example.timeouts.connect-unit: minutes",
    "rest.clients.example.timeouts.read: 34",
    "rest.clients.example.timeouts.read-unit: hours"
})
@Component
public final class ClientConfigurationTest {

    @Autowired
    @Qualifier("example")
    private Rest exampleRest;

    @Autowired
    @Qualifier("ecb")
    private Rest ecbRest;

    @Autowired
    @Qualifier("example")
    private RestTemplate exampleRestTemplate;

    @Autowired
    @Qualifier("example")
    private AsyncRestTemplate exampleAsyncRestTemplate;

    @Autowired
    @Qualifier("example")
    private HttpClient exampleHttpClient;

    @Test
    public void shouldWireOAuthCorrectly() {
        assertThat(exampleRest, is(notNullValue()));
        assertThat(exampleRestTemplate, is(notNullValue()));
        assertThat(exampleAsyncRestTemplate, is(notNullValue()));
    }

    @Test
    public void shouldWireNonOAuthCorrectly() {
        assertThat(ecbRest, is(notNullValue()));
    }

    @Test
    public void shouldApplyTimeouts() throws Exception {
        assertThat("Configurable http client expected", exampleHttpClient, is(instanceOf(Configurable.class)));

        final RequestConfig config = ((Configurable) exampleHttpClient).getConfig();

        assertThat(config.getSocketTimeout(), is(34 * 60 * 60 * 1000));
        assertThat(config.getConnectTimeout(), is(12 * 60 * 1000));
    }

}
