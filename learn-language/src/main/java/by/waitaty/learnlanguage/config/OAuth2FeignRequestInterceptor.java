package by.waitaty.learnlanguage.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Component;

@Component
public class OAuth2FeignRequestInterceptor implements RequestInterceptor {

    private final AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager;
    private final ClientRegistration clientRegistration;

    @Autowired
    public OAuth2FeignRequestInterceptor(OAuth2ClientManager oAuth2ClientManager,
                                         ClientRegistrationRepository clientRegistrationRepository) {
        this.authorizedClientManager = oAuth2ClientManager.getAuthorizedClientManager();
        this.clientRegistration = clientRegistrationRepository.findByRegistrationId("messaging-client-token-exchange");
    }

    @Override
    public void apply(RequestTemplate template) {
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest.withClientRegistrationId(clientRegistration.getRegistrationId())
                .principal("client")
                .build();
        OAuth2AuthorizedClient authorizedClient = authorizedClientManager.authorize(authorizeRequest);
        String accessToken = authorizedClient.getAccessToken().getTokenValue();
        template.header("Authorization", "Bearer " + accessToken);
    }
}
