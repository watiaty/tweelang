package by.waitaty.learnlanguage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@Configuration
public class FeignClientConfiguration {

    @Bean
    public OAuth2FeignRequestInterceptor oauth2FeignRequestInterceptor(OAuth2ClientManager oAuth2ClientManager,
                                                                       ClientRegistrationRepository clientRegistrationRepository) {
        return new OAuth2FeignRequestInterceptor(oAuth2ClientManager, clientRegistrationRepository);
    }
}
