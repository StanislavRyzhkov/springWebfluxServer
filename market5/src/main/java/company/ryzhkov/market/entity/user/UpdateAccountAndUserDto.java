package company.ryzhkov.market.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateAccountAndUserDto {
    private final UpdateAccountDto updateAccountDto;
    private final User user;
}
