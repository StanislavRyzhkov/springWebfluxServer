package company.ryzhkov.market.entity.user;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class AccountDto implements Serializable {
    private final String username;
    private final String email;
    private final String firstName;
    private final String secondName;
    private final String phoneNumber;

    public AccountDto() {
        this.username = "";
        this.email = "";
        this.firstName = "";
        this.secondName = "";
        this.phoneNumber = "";
    }

    private AccountDto(String username, String email, String firstName, String secondName, String phoneNumber
    ) {
        this.username = username;
        this.email = email;
        this.firstName = firstName == null ? "" : firstName;
        this.secondName = secondName == null ? "" : secondName;
        this.phoneNumber = phoneNumber == null ? "" : phoneNumber;
    }

    public static AccountDto createInstance(User user) {
        return new AccountDto(
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getSecondName(),
                user.getPhoneNumber()
        );
    }
}
