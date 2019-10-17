package company.ryzhkov.market.security;

import company.ryzhkov.market.exception.AuthException;
import company.ryzhkov.market.repository.KeyElementRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static company.ryzhkov.market.constants.Constants.ACCESS_DENIED;

@Slf4j
@Component
public class TokenProvider {
    private ReactiveUserDetailsService userService;
    private KeyElementRepository keyElementRepository;
    private Key key;

    private void setKey(Key key) { this.key = key; }

    @Autowired
    @Qualifier("us")
    public void setUserService(ReactiveUserDetailsService userService) { this.userService = userService; }

    @Autowired
    public void setKeyElementRepository(KeyElementRepository keyElementRepository) {
        this.keyElementRepository = keyElementRepository;
    }

    @Value("${jwt.token.expired}")
    private long expired;

    @PostConstruct
    public void init() {
        keyElementRepository.findAll().take(1).subscribe(e -> {
            byte[] bytes = Base64.getDecoder().decode(e.getSecretString());
            this.setKey(new SecretKeySpec(bytes, SignatureAlgorithm.HS256.getJcaName()));
        });
    }

    Mono<? extends Authentication> getAuthentication(ServerHttpRequest request) {
        return getUsername(request)
                .flatMap(userService::findByUsername)
                .map(ud -> new UsernamePasswordAuthenticationToken(ud, "", ud.getAuthorities()))
                .onErrorResume(e -> Mono.just(new UsernamePasswordAuthenticationToken("", "")));
    }

    public String createToken(Authentication authentication) {
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        List<String> authorities = authentication
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return createToken(username, authorities);
    }

    public String createToken(String username) { return createToken(username, Collections.singletonList("ROLE_USER")); }

    private String createToken(String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);
        Date now = new Date();
        Date validity = new Date(now.getTime() + expired);
        return Jwts
                .builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key)
                .compact();
    }

    public Mono<String> getUsername(ServerHttpRequest request) {
        return Mono.fromCallable(() -> {
            List<String> list = request.getHeaders().get("Authorization");
            if (list == null || list.isEmpty()) throw new AuthException(ACCESS_DENIED);
            String authHeader = list.get(0);
            if (authHeader == null || !authHeader.startsWith("Bearer")) throw new AuthException(ACCESS_DENIED);
            String token = authHeader.substring("Bearer".length()).trim();
            try {
                return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
            } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException |
                    IllegalArgumentException | SignatureException e) {
                throw new AuthException(ACCESS_DENIED, e);
            } catch (Exception e) {
                log.warn("java.lang.Exception has been thrown in TokenProvider");
                throw new AuthException(ACCESS_DENIED, e);
            }
        });
    }
}
