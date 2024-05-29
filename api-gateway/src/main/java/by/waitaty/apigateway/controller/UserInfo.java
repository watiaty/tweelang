package by.waitaty.apigateway.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class UserInfo {

    @GetMapping("/me")
    public Mono<UserDto> userInfo(@AuthenticationPrincipal OidcUser principal) {
        if (principal != null) {
            List<String> list = principal.getIdToken().getClaimAsStringList("authorities");
            String authorities = String.join(",", list);
            return Mono.just(
                    new UserDto(
                            principal.getIdToken().getClaim("userId"),
                            principal.getIdToken().getSubject(),
                            authorities));
        }
        return Mono.just(UserDto.ANONYMOUS);
    }

    record UserDto(Long id, String username, String roles) {
        static final UserDto ANONYMOUS = new UserDto(0L, "ANONYMOUS", "NONE");
    }
}
