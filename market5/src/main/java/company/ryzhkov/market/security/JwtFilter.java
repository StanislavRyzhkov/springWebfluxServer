package company.ryzhkov.market.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class JwtFilter implements WebFilter {
    private TokenProvider provider;

    @Autowired
    public void setProvider(TokenProvider provider) { this.provider = provider; }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return provider
                .getAuthentication(exchange.getRequest())
                .map(ReactiveSecurityContextHolder::withAuthentication)
                .flatMap(context -> chain.filter(exchange).subscriberContext(context));
    }
}
