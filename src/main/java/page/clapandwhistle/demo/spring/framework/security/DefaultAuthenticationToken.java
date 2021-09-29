package page.clapandwhistle.demo.spring.framework.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

final public class DefaultAuthenticationToken extends UsernamePasswordAuthenticationToken {
    final private long principalId;
    final private String principal;

    public DefaultAuthenticationToken(
            String principal,
            String credentials,
            Collection<? extends GrantedAuthority> authorities,
            long principalId
    ) {
        super(principal, credentials, authorities);
        this.principal = principal;
        this.principalId = principalId;
    }

    public String getName() {
        return this.principal;
    }

    public long getPrincipalId() {
        return principalId;
    }
}
