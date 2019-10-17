package company.ryzhkov.market.entity.user;

import lombok.Getter;

@Getter
public class UsernameEmailPasswordDto {
    private final String username;
    private final String email;
    private final String password;

    private UsernameEmailPasswordDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public static UsernameEmailPasswordDto createInstance(RegistrationDto dto) {
        return new UsernameEmailPasswordDto(dto.getUsername(), dto.getEmail(), dto.getPassword1());
    }
}
