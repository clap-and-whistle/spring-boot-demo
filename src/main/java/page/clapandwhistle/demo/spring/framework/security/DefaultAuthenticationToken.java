package page.clapandwhistle.demo.spring.framework.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

final public class DefaultAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public DefaultAuthenticationToken(
            String principal,
            String credentials,
            Collection<? extends GrantedAuthority> authorities,
            long principalId
    ) {
        super(principal, credentials, authorities);
    }

}
