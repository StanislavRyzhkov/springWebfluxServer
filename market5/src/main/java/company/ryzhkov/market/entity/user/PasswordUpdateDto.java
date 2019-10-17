package company.ryzhkov.market.entity.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import company.ryzhkov.market.util.PasswordRepeatMatch;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static company.ryzhkov.market.constants.Constants.PASSWORD_TOO_LONG;
import static company.ryzhkov.market.constants.Constants.PASSWORD_TOO_SHORT;
import static company.ryzhkov.market.constants.Constants.PASSWORD_FIELD_IS_EMPTY;

@Getter
@PasswordRepeatMatch(first = "newPassword1", second = "newPassword2")
public class PasswordUpdateDto {

    @NotBlank(message = PASSWORD_FIELD_IS_EMPTY)
    @Size(max = 64, message = PASSWORD_TOO_LONG)
    @Size(min = 5, message = PASSWORD_TOO_SHORT)
    private final String oldPassword;

    @NotBlank(message = PASSWORD_FIELD_IS_EMPTY)
    @Size(max = 64, message = PASSWORD_TOO_LONG)
    @Size(min = 5, message = PASSWORD_TOO_SHORT)
    private final String newPassword1;

    @NotBlank(message = PASSWORD_FIELD_IS_EMPTY)
    @Size(max = 64, message = PASSWORD_TOO_LONG)
    @Size(min = 5, message = PASSWORD_TOO_SHORT)
    private final String newPassword2;

    @JsonCreator
    public PasswordUpdateDto(
            @JsonProperty("oldPassword") String oldPassword,
            @JsonProperty("newPassword1") String newPassword1,
            @JsonProperty("newPassword2") String newPassword2
    ) {
        this.oldPassword = oldPassword;
        this.newPassword1 = newPassword1;
        this.newPassword2 = newPassword2;
    }

    public PasswordUpdateAndUsernameDto toPasswordUpdateAndUsernameDto(String username) {
        return new PasswordUpdateAndUsernameDto(this, username);
    }
}
