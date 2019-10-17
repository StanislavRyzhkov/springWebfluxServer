package company.ryzhkov.market.service;

import company.ryzhkov.market.entity.user.*;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import reactor.core.publisher.Mono;

public interface UserService extends ReactiveUserDetailsService {

    Mono<String> register(Mono<RegistrationDto> dto);

    Mono<AccountDto> getAccount(String username);

    Mono<String> updateAccount(UpdateAccountAndUsernameDto dto);

    Mono<String> deleteAccount(DeleteAccountAndUsernameDto dto);

    Mono<String> updatePassword(PasswordUpdateAndUsernameDto dto);
}
