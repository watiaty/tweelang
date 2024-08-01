package by.waitaty.learnlanguage.config;

import lombok.Getter;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Component;

@Getter
@Component
public class OAuth2ClientManager {

    private final AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager;

    public OAuth2ClientManager(ClientRegistrationRepository clientRegistrationRepository,
                               OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientManager = new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                clientRegistrationRepository, authorizedClientService);
    }

}
