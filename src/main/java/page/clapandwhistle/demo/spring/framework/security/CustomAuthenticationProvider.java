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
import page.clapandwhistle.demo.spring.bizlogic.uam.UseCase.UserOperation.Login.Arguments;
import page.clapandwhistle.demo.spring.bizlogic.uam.UseCase.UserOperation.Login.LoginUseCase;
import page.clapandwhistle.demo.spring.bizlogic.uam.UseCase.UserOperation.Login.Result;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    final private LoginUseCase useCase;

    @Autowired
    public CustomAuthenticationProvider(LoginUseCase useCase) {
        this.useCase = useCase;
    }

    @Transactional
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        try {
            Result result = useCase.execute(new Arguments(email, password));
            if (result.isSuccess()) {
                Collection<GrantedAuthority> authorities = new ArrayList<>();
                // TODO: 暫定的に ROLE_ADMIN を与えているが、システム管理アカウント機能を実装する際には直す
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                return new UsernamePasswordAuthenticationToken(email, password, authorities);
            } else {
                throw new BadCredentialsException(result.eMessage());
            }
        } catch (Exception e) {
            throw new AuthenticationException(e.getMessage()) {};
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        System.out.println("CustomAuthenticationProvider::supports");
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
