package company.ryzhkov.market.security;

import company.ryzhkov.market.entity.message.Message;
import company.ryzhkov.market.exception.AlreadyExistsException;
import company.ryzhkov.market.exception.AuthException;
import company.ryzhkov.market.exception.NotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import static company.ryzhkov.market.util.Util.messageOnInvalid;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class CommonExceptionHandler {

    @ResponseBody
    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(AccessDeniedException.class)
    public Mono<Message> handleInvalidAuth(AccessDeniedException e) { return Mono.just(Message.ACCESS_DENIED); }

    @ResponseBody
    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(AuthException.class)
    public Mono<Message> handleInvalidAuth(AuthException e) { return Mono.just(new Message(e.getMessage())); }

    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<Message> handleInvalidInput(WebExchangeBindException e) { return Mono.just(messageOnInvalid(e)); }

    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(BadCredentialsException.class)
    public Mono<Message> handleInvalidAuth(BadCredentialsException e) { return Mono.just(new Message(e.getMessage())); }

    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(AlreadyExistsException.class)
    public Mono<Message> handleInvalidAuth(AlreadyExistsException e) { return Mono.just(new Message(e.getMessage())); }

    @ResponseBody
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public Mono<Message> handleInvalidAuth(NotFoundException e) { return Mono.just(new Message(e.getMessage())); }
}
