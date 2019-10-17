package company.ryzhkov.market.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteAccountAndUsernameDto {
    private final DeleteAccountDto deleteAccountDto;
    private final String username;
}
