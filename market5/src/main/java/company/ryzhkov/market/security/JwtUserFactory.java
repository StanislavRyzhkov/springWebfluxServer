package company.ryzhkov.market.security;

import company.ryzhkov.market.entity.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public final class JwtUserFactory {

    public JwtUserFactory() {}

    public static UserDetails create(User user) {
        boolean enabled = user.getStatus().equals("ACTIVE");
        return new JwtUser(user.getUsername(), user.getPassword(), enabled, mapRoles(user.getRoles()));
    }

    private static Collection<? extends GrantedAuthority> mapRoles(List<String> roles) {
        return roles.stream().map(SimpleGrantedAuthority::new).collect(toList());
    }
}
