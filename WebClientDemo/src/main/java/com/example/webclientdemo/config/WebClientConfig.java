package com.example.webclientdemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.webclient.WebClientCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig implements WebClientCustomizer {

    private final String rootUrl;
    private final ReactiveOAuth2AuthorizedClientManager manager;

    public WebClientConfig(@Value("${webclient.rooturl}") String rootUrl,
                           ReactiveOAuth2AuthorizedClientManager manager) {
        this.rootUrl = rootUrl;
        this.manager = manager;
    }

    @Override
    public void customize(WebClient.Builder webClientBuilder) {
        ServerOAuth2AuthorizedClientExchangeFilterFunction authExchangeFilter =
                new ServerOAuth2AuthorizedClientExchangeFilterFunction(manager);

        authExchangeFilter.setDefaultClientRegistrationId("springauth");

        webClientBuilder.filter(authExchangeFilter).baseUrl(rootUrl);
    }
}
