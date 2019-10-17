package company.ryzhkov.market.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PasswordUpdateAndUsernameDto {
    private final PasswordUpdateDto passwordUpdateDto;
    private final String username;
}
