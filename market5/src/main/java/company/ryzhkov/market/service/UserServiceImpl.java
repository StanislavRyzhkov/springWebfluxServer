package company.ryzhkov.market.service;

import company.ryzhkov.market.entity.user.*;
import company.ryzhkov.market.exception.AlreadyExistsException;
import company.ryzhkov.market.exception.AuthException;
import company.ryzhkov.market.exception.NotFoundException;
import company.ryzhkov.market.repository.UserRepository;
import company.ryzhkov.market.security.JwtUserFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static company.ryzhkov.market.constants.Constants.INVALID_USERNAME_OR_PASSWORD;

@Slf4j
@Service("us")
public class UserServiceImpl implements UserService {
    private final static String USER_NOT_FOUND = "Пользователь не найден";
    private final static String INVALID_PASSWORD = "Неправильный пароль";
    private final static String USER_ALREADY_EXISTS = "Пользователь с таким именем уже существует";
    private final static String EMAIL_ALREADY_EXISTS = "Пользователь с таким email существует";
    private final static String USER_CREATED = "Пользователь создан";
    private final static String USER_UPDATED = "Пользователь изменен";
    private final static String USER_DELETED = "Пользователь удален";
    private final static String PASSWORD_UPDATED = "Пароль изменен";

    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) { this.userRepository = userRepository; }

    @Autowired
    public void setEncoder(BCryptPasswordEncoder encoder) { this.encoder = encoder; }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return findActiveUserByUsername(username)
                .map(JwtUserFactory::create)
                .onErrorMap(NotFoundException.class, e -> new BadCredentialsException(INVALID_USERNAME_OR_PASSWORD));
    }

    @Override
    public Mono<String> register(Mono<RegistrationDto> dto) {
        return dto
                .map(UsernameEmailPasswordDto::createInstance)
                .flatMap(this::checkCredentialsUniqueAndSave)
                .map(e -> USER_CREATED);
    }

    @Override
    public Mono<AccountDto> getAccount(String username) {
        return findActiveUserByUsername(username).map(AccountDto::createInstance);
    }

    @Override
    public Mono<String> updateAccount(UpdateAccountAndUsernameDto dto) {
        return toUpdateAccountAndUserDto(dto).flatMap(this::updateAccount).map(e -> USER_UPDATED);
    }

    @Override
    public Mono<String> deleteAccount(DeleteAccountAndUsernameDto dto) {
        return toDeleteAccountAndUserDto(dto).flatMap(this::deleteAccount).map(e -> USER_DELETED);
    }

    @Override
    public Mono<String> updatePassword(PasswordUpdateAndUsernameDto dto) {
        return toPasswordUpdateAndUserDto(dto).flatMap(this::updatePassword).map(e -> PASSWORD_UPDATED);
    }

    private Mono<User> checkCredentialsUniqueAndSave(UsernameEmailPasswordDto dto) {
        return Mono
                .zip(checkUsernameUnique(dto.getUsername()), checkEmailUnique(dto.getEmail()))
                .flatMap(e -> {
                    if (!e.getT1()) throw new AlreadyExistsException(USER_ALREADY_EXISTS);
                    if (!e.getT2()) throw new AlreadyExistsException(EMAIL_ALREADY_EXISTS);
                    return userRepository.insert(User.createInstance(dto, encoder));
                })
                .doOnNext(u -> log.info("{} created", u.toString()));
    }

    private Mono<User> findAnyUserByUsername(String username) {
        return userRepository.findByUsername(username).defaultIfEmpty(new User()).map(user -> {
            if (user.getId() == null) throw new NotFoundException(USER_NOT_FOUND);
            return user;
        });
    }

    private Mono<User> findAnyUserByEmail(String email) {
        return userRepository.findByEmail(email).defaultIfEmpty(new User()).map(user -> {
            if (user.getId() == null) throw new NotFoundException(USER_NOT_FOUND);
            return user;
        });
    }

    private Mono<User> findActiveUserByUsername(String username) {
        return userRepository.findByUsernameAndStatus(username, "ACTIVE").defaultIfEmpty(new User()).map(user -> {
            if (user.getId() == null) throw new NotFoundException(USER_NOT_FOUND);
            return user;
        });
    }

    private Mono<UpdateAccountAndUserDto> toUpdateAccountAndUserDto(UpdateAccountAndUsernameDto dto) {
        return findActiveUserByUsername(dto.getUsername())
                .map(user -> new UpdateAccountAndUserDto(dto.getUpdateAccountDto(), user));
    }

    private Mono<User> updateAccount(UpdateAccountAndUserDto dto) {
        User user = dto.getUser();
        user.setFirstName(dto.getUpdateAccountDto().getFirstName());
        user.setSecondName(dto.getUpdateAccountDto().getSecondName());
        user.setPhoneNumber(dto.getUpdateAccountDto().getPhoneNumber());
        return userRepository.save(user).doOnNext(u ->  log.info("{} updated", u.toString()));
    }

    private Mono<DeleteAccountAndUserDto> toDeleteAccountAndUserDto(DeleteAccountAndUsernameDto dto) {
        return findActiveUserByUsername(dto.getUsername())
                .map(user -> new DeleteAccountAndUserDto(dto.getDeleteAccountDto(), user));
    }

    private Mono<User> deleteAccount(DeleteAccountAndUserDto dto) {
        if (!dto.getDeleteAccountDto().getUsername().equals(dto.getUser().getUsername()))
            throw new AuthException(INVALID_USERNAME_OR_PASSWORD);
        if (!encoder.matches(dto.getDeleteAccountDto().getPassword1(), dto.getUser().getPassword()))
            throw new AuthException(INVALID_USERNAME_OR_PASSWORD);
        User user = dto.getUser();
        user.setStatus("INACTIVE");
        return userRepository.save(user).doOnNext(u -> log.info("{} deleted", u.toString()));
    }

    private Mono<PasswordUpdateAndUserDto> toPasswordUpdateAndUserDto(PasswordUpdateAndUsernameDto dto) {
        return findActiveUserByUsername(dto.getUsername())
                .map(user -> new PasswordUpdateAndUserDto(dto.getPasswordUpdateDto(), user));
    }

    private Mono<User> updatePassword(PasswordUpdateAndUserDto dto) {
        if (!encoder.matches(dto.getPasswordUpdateDto().getOldPassword(), dto.getUser().getPassword()))
            throw new AuthException(INVALID_PASSWORD);
        User user = dto.getUser();
        user.setPassword(encoder.encode(dto.getPasswordUpdateDto().getNewPassword1()));
        return userRepository.save(user).doOnNext(u -> log.info("{} password updated", u.toString()));
    }

    private Mono<Boolean> checkUsernameUnique(String username) {
        return findAnyUserByUsername(username)
                .map(e -> false)
                .onErrorResume(NotFoundException.class, e -> Mono.just(true));
    }

    private Mono<Boolean> checkEmailUnique(String email) {
        return findAnyUserByEmail(email)
                .map(e -> false)
                .onErrorResume(NotFoundException.class, e -> Mono.just(true));
    }
}
