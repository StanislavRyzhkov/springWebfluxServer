package company.ryzhkov.market.entity.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import company.ryzhkov.market.util.PasswordRepeatMatch;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static company.ryzhkov.market.constants.Constants.*;

@Getter
@PasswordRepeatMatch(first = "password1", second = "password2")
public class RegistrationDto implements Serializable {

    @NotBlank(message = USERNAME_FIELD_IS_EMPTY)
    @Size(max = 64, message = USERNAME_TOO_LONG)
    private final String username;

    @NotBlank(message = EMAIL_FIELD_IS_EMPTY)
    @Email(message = INVALID_EMAIL)
    @Size(max = 64, message = EMAIL_TOO_LONG)
    private final String email;

    @NotBlank(message = PASSWORD_FIELD_IS_EMPTY)
    @Size(max = 64, message = PASSWORD_TOO_LONG)
    @Size(min = 5, message = PASSWORD_TOO_SHORT)
    private final String password1;

    @NotBlank(message = PASSWORD_FIELD_IS_EMPTY)
    @Size(max = 64, message = PASSWORD_TOO_LONG)
    @Size(min = 5, message = PASSWORD_TOO_SHORT)
    private final String password2;

    @JsonCreator
    public RegistrationDto(
            @JsonProperty("username") String username,
            @JsonProperty("email") String email,
            @JsonProperty("password1") String password1,
            @JsonProperty("password2") String password2
    ) {
        this.username = username;
        this.email = email;
        this.password1 = password1;
        this.password2 = password2;
    }
}
