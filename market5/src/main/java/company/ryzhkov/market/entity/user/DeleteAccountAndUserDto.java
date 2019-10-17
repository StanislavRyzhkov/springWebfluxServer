package company.ryzhkov.market.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteAccountAndUserDto {
    private final DeleteAccountDto deleteAccountDto;
    private final User user;
}
