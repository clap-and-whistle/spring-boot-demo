package page.clapandwhistle.demo.spring.framework.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import page.clapandwhistle.demo.spring.infrastructure.uam.TableModel.UserAccountBase;

import java.util.ArrayList;
import java.util.Collection;

final public class AuthUserDetails implements UserDetails {
    private final UserAccountBase myUser;
    private final Collection<GrantedAuthority> authorities;

    public AuthUserDetails(UserAccountBase myUser) {
        this.myUser = myUser;
        this.authorities = new ArrayList<>();
        this.authorities.add(new SimpleGrantedAuthority(myUser.getRole()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.myUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.myUser.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
