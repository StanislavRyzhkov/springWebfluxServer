package company.ryzhkov.market.entity.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import company.ryzhkov.market.util.AccountPhoneNumberMatch;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@AccountPhoneNumberMatch(fieldName = "phoneNumber")
public class UpdateAccountDto implements Serializable {
    private final static String INVALID_FIRST_NAME = "Некорректная длина имени";
    private final static String INVALID_SECOND_NAME = "Некорректная длина фамилии";
    private final static String INVALID_PHONE_NUMBER_LENGTH = "Некорректная длина номера телефона";
    private final static String INVALID_PHONE_NUMBER_FORMAT = "Некорректный формат номера телефона";

    @NotNull
    @Size(max = 120, message = INVALID_FIRST_NAME)
    private final String firstName;

    @NotNull
    @Size(max = 120, message = INVALID_SECOND_NAME)
    private final String secondName;

    @NotNull
    @Size(max = 120, message = INVALID_PHONE_NUMBER_LENGTH)
    private final String phoneNumber;

    @JsonCreator
    public UpdateAccountDto(
            @JsonProperty("firstName") String firstName,
            @JsonProperty("secondName") String secondName,
            @JsonProperty("phoneNumber") String phoneNumber
    ) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.phoneNumber = phoneNumber;
    }

    public UpdateAccountAndUsernameDto toUpdateAccountAndUsernameDto(String username) {
        return new UpdateAccountAndUsernameDto(this, username);
    }
}
