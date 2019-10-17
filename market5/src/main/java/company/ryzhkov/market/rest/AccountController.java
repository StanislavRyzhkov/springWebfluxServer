package company.ryzhkov.market.rest;

import company.ryzhkov.market.entity.message.Message;
import company.ryzhkov.market.entity.user.AccountDto;
import company.ryzhkov.market.entity.user.DeleteAccountDto;
import company.ryzhkov.market.entity.user.PasswordUpdateDto;
import company.ryzhkov.market.entity.user.UpdateAccountDto;
import company.ryzhkov.market.security.TokenProvider;
import company.ryzhkov.market.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/user_area")
public class AccountController {
    private TokenProvider tokenProvider;
    private UserService userService;

    @Autowired
    public void setTokenProvider(TokenProvider tokenProvider) { this.tokenProvider = tokenProvider; }

    @Autowired
    public void setUserService(UserService userService) { this.userService = userService; }

    @GetMapping("username")
    @PreAuthorize("hasRole('USER')")
    public Mono<Message> getUsername(ServerHttpRequest request) {
        return tokenProvider.getUsername(request).map(Message::new);
    }

    @GetMapping("account")
    @PreAuthorize("hasRole('USER')")
    public Mono<AccountDto> getAccount(ServerHttpRequest request) {
        return tokenProvider.getUsername(request).flatMap(userService::getAccount);
    }

    @PutMapping("account")
    @PreAuthorize("hasRole('USER')")
    public Mono<Message> updateAccount(
            ServerHttpRequest request,
            @Valid @RequestBody Mono<UpdateAccountDto> updateAccountDto
    ) {
        return Mono.zip(updateAccountDto, tokenProvider.getUsername(request))
                .map(e -> e.getT1().toUpdateAccountAndUsernameDto(e.getT2()))
                .flatMap(userService::updateAccount)
                .map(Message::new);
    }

    @DeleteMapping("account")
    @PreAuthorize("hasRole('USER')")
    public Mono<Message> deleteAccount(
            ServerHttpRequest request,
            @Valid @RequestBody Mono<DeleteAccountDto> deleteAccountDto
    ) {
        return Mono.zip(deleteAccountDto, tokenProvider.getUsername(request))
                .map(e -> e.getT1().toDeleteAccountAndUsernameDto(e.getT2()))
                .flatMap(userService::deleteAccount)
                .map(Message::new);
    }

    @PutMapping("password")
    @PreAuthorize("hasRole('USER')")
    public Mono<Message> updatePassword(
            ServerHttpRequest request,
            @Valid @RequestBody Mono<PasswordUpdateDto> passwordUpdateDto
    ) {
        return Mono.zip(passwordUpdateDto, tokenProvider.getUsername(request))
                .map(e -> e.getT1().toPasswordUpdateAndUsernameDto(e.getT2()))
                .flatMap(userService::updatePassword)
                .map(Message::new);
    }
}
