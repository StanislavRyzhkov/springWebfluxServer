package company.ryzhkov.market.config;

import company.ryzhkov.market.security.CustomReactiveAuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {
    private ReactiveUserDetailsService userService;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) { this.passwordEncoder = passwordEncoder; }

    @Autowired
    @Qualifier("us")
    public void setUserService(ReactiveUserDetailsService userService) { this.userService = userService; }

    @Bean("am")
    public CustomReactiveAuthenticationManager authenticationManager() {
        CustomReactiveAuthenticationManager am = new CustomReactiveAuthenticationManager();
        am.setPasswordEncoder(passwordEncoder);
        am.setUserDetailsService(userService);
        return am;
    }

    @Bean
    public SecurityWebFilterChain authorization(ServerHttpSecurity http) {
        return http
                .httpBasic().disable()
                .csrf().disable()
                .authorizeExchange()
                .anyExchange().permitAll()
                .and().cors()
                .and().build();
    }
}
