package company.ryzhkov.market.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateAccountAndUsernameDto {
    private final UpdateAccountDto updateAccountDto;
    private final String username;
}
