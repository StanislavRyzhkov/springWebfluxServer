package company.ryzhkov.market.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PasswordUpdateAndUserDto {
    private final PasswordUpdateDto passwordUpdateDto;
    private final User user;
}
