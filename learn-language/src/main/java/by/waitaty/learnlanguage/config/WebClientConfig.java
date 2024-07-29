package by.waitaty.learnlanguage.config;

import by.waitaty.learnlanguage.client.WordClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientConfig {
    private final LoadBalancedExchangeFilterFunction filterFunction;

    @Autowired
    public WebClientConfig(LoadBalancedExchangeFilterFunction filterFunction) {
        this.filterFunction = filterFunction;
    }

    public WordClient wordClient(OAuth2AuthorizedClient authorizedClient) {
        WebClient webClient = WebClient.builder()
                .baseUrl("http://word-service")
                .filter(filterFunction)
                .defaultHeaders((headers) -> headers.setBearerAuth(authorizedClient.getAccessToken().getTokenValue()))
                .build();

        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient))
                .build();
        return httpServiceProxyFactory.createClient(WordClient.class);
    }
}
