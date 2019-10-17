package company.ryzhkov.market.test;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    @PreAuthorize("hasRole('USER') and hasRole('ADMIN')")
    public Mono<String> helloMessage() {
        return Mono.just("Hello, world!");
    }
}
