package page.clapandwhistle.demo.spring.framework.security;

import org.springframework.security.test.context.support.WithSecurityContext;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.AdminUser.AdminUser;
import unit.infrastructure.uam.AggregateRepository.AdminUser.ForTestAdminUserAggregateRepository;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {
    long id();
    String email();
    String password() default "hoge01TEST";
    String role();
}