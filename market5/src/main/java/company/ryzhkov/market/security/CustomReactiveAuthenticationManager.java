package company.ryzhkov.market.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

import static company.ryzhkov.market.constants.Constants.INVALID_USERNAME_OR_PASSWORD;

public class CustomReactiveAuthenticationManager implements ReactiveAuthenticationManager {
    private ReactiveUserDetailsService userDetailsService;
    private PasswordEncoder encoder;

    public void setUserDetailsService(ReactiveUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public void setPasswordEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return userDetailsService
                .findByUsername(authentication.getName())
                .map(e -> {
                    String password = (String) authentication.getCredentials();
                    if (!encoder.matches(password, e.getPassword()))
                        throw new BadCredentialsException(INVALID_USERNAME_OR_PASSWORD);
                    return e;
                })
                .map(e -> new UsernamePasswordAuthenticationToken(e, "", e.getAuthorities()));
    }
}
