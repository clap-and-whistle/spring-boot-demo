package page.clapandwhistle.demo.spring.framework.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.AdminUserAggregateRepositoryInterface;
import page.clapandwhistle.demo.spring.bizlogic.uam.UseCase.SysAdminOperation.Login.LoginUseCase;
import page.clapandwhistle.demo.spring.bizlogic.uam.UseCase.SysAdminOperation.Login.Result;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class AdminAuthenticationProvider implements AuthenticationProvider {
    final private LoginUseCase useCase;

    @Autowired
    public AdminAuthenticationProvider(AdminUserAggregateRepositoryInterface aggregateRepos) {
        this.useCase = new LoginUseCase(aggregateRepos);
    }

    @Transactional
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("AdminAuthenticationProvider: Authentication");
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        try {
            Result result = useCase.execute(email, password);
            if (result.isSuccess()) {
                Collection<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                return new DefaultAuthenticationToken(email, password, authorities, result.adminId());
            } else {
                throw new BadCredentialsException(result.eMessage());
            }
        } catch (Exception e) {
            throw new AuthenticationException(e.getMessage()) {};
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        System.out.println("AdminAuthenticationProvider::supports");
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
