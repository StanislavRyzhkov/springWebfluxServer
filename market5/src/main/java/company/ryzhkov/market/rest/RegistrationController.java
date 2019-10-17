package company.ryzhkov.market.rest;

import company.ryzhkov.market.entity.message.Message;
import company.ryzhkov.market.entity.user.RegistrationDto;
import company.ryzhkov.market.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/register")
public class RegistrationController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) { this.userService = userService; }

    @PostMapping
    public Mono<Message> register(@Valid @RequestBody Mono<RegistrationDto> regDto) {
        return userService.register(regDto).map(Message::new);
    }
}
