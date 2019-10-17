package company.ryzhkov.market.entity.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static company.ryzhkov.market.constants.Constants.*;

@Getter
public class AuthenticationDto implements Serializable {

    @NotBlank(message = USERNAME_FIELD_IS_EMPTY)
    @Size(max = 64, message = USERNAME_TOO_LONG)
    private final String username;

    @NotBlank(message = PASSWORD_FIELD_IS_EMPTY)
    @Size(max = 64, message = PASSWORD_TOO_LONG)
    private final String password;

    @JsonCreator
    public AuthenticationDto(@JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }
}
