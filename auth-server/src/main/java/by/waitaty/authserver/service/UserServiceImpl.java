package by.waitaty.authserver.service;

import by.waitaty.authserver.model.Language;
import by.waitaty.authserver.model.Role;
import by.waitaty.authserver.model.User;
import by.waitaty.authserver.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;

    @Transactional
    public User createUserFromGoogle(OidcUser oidcUser) {
        User user = User.builder()
                .username(oidcUser.getEmail())
                .email(oidcUser.getEmail())
                .firstName(oidcUser.getGivenName())
                .lastName(oidcUser.getFamilyName())
                .nativeLang(Language.EN) // Assuming a default language, modify as needed
                .role(Role.USER) // Assuming a default role, modify as needed
                .build();
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow();
    }
}
