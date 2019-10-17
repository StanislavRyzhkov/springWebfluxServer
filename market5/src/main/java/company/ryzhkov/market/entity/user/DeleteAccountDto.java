package company.ryzhkov.market.entity.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import company.ryzhkov.market.util.PasswordRepeatMatch;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static company.ryzhkov.market.constants.Constants.*;

@Getter
@PasswordRepeatMatch(first = "password1", second = "password2")
public class DeleteAccountDto {

    @NotBlank(message = USERNAME_FIELD_IS_EMPTY)
    @Size(max = 64, message = USERNAME_TOO_LONG)
    private final String username;

    @NotBlank(message = PASSWORD_FIELD_IS_EMPTY)
    @Size(max = 64, message = PASSWORD_TOO_LONG)
    @Size(min = 5, message = PASSWORD_TOO_SHORT)
    private final String password1;

    @NotBlank(message = PASSWORD_FIELD_IS_EMPTY)
    @Size(max = 64, message = PASSWORD_TOO_LONG)
    @Size(min = 5, message = PASSWORD_TOO_SHORT)
    private final String password2;

    @JsonCreator
    public DeleteAccountDto(
            @JsonProperty("username") String username,
            @JsonProperty("password1") String newPassword1,
            @JsonProperty("password2") String newPassword2
    ) {
        this.username = username;
        this.password1 = newPassword1;
        this.password2 = newPassword2;
    }

    public DeleteAccountAndUsernameDto toDeleteAccountAndUsernameDto(String username) {
        return new DeleteAccountAndUsernameDto(this, username);
    }
}
