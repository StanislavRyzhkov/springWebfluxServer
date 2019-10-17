package company.ryzhkov.market.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.List;

import static java.util.Collections.singletonList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String secondName;
    private String phoneNumber;
    private String status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date created;
    private List<String> roles;

    @Override
    public String toString() {
        return String.format("%s %s\n", username, email);
    }

    public static User createInstance(UsernameEmailPasswordDto dto, PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(singletonList("ROLE_USER"));
        user.setStatus("ACTIVE");
        user.setCreated(new Date());
        return user;
    }
}
