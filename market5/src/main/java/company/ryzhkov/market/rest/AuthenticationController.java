package company.ryzhkov.market.rest;

import company.ryzhkov.market.entity.message.Message;
import company.ryzhkov.market.entity.user.AuthenticationDto;
import company.ryzhkov.market.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
public class AuthenticationController {
    private ReactiveAuthenticationManager manager;
    private TokenProvider provider;

    @Autowired
    @Qualifier("am")
    public void setManager(ReactiveAuthenticationManager manager) { this.manager = manager; }

    @Autowired
    public void setTokenProvider(TokenProvider provider) { this.provider = provider; }

    @PostMapping
    public Mono<Message> authenticate(@Valid @RequestBody Mono<AuthenticationDto> dto) {
        return dto
                .flatMap(e -> manager.authenticate(new UsernamePasswordAuthenticationToken(e.getUsername(), e.getPassword())))
                .map(provider::createToken)
                .map(Message::new);
    }
}
