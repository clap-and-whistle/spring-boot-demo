package page.clapandwhistle.demo.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import page.clapandwhistle.demo.spring.controller.admin.SystemConsoleController;
import page.clapandwhistle.demo.spring.controller.desk.MyWorkController;
import page.clapandwhistle.demo.spring.controller.open.HomeController;
import page.clapandwhistle.demo.spring.controller.uam.SysAdminOperation.AdmLoginController;
import page.clapandwhistle.demo.spring.controller.uam.UserOperation.CreateAccountController;
import page.clapandwhistle.demo.spring.controller.uam.UserOperation.LoginController;
import page.clapandwhistle.demo.spring.framework.security.AdminAuthenticationFailureHandler;
import page.clapandwhistle.demo.spring.framework.security.AdminAuthenticationProvider;
import page.clapandwhistle.demo.spring.framework.security.CustomAuthenticationProvider;
import page.clapandwhistle.demo.spring.framework.security.CustomAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class WebAuthSecurityConfiguration {
    public static final String[] IGNOREING_COMMON_MATCHERS = {
            "/css/**",
            "/js/**",
            "/open/**",
    };

    private static HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = null;

    private static HttpSessionCsrfTokenRepository getHttpSessionCsrfTokenRepositoryInstance() {
        if (httpSessionCsrfTokenRepository == null) {
            WebAuthSecurityConfiguration.httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        }
        return httpSessionCsrfTokenRepository;
    }

    @Configuration
    @Order(2)
    public static class UserSecurityConfiguration extends WebSecurityConfigurerAdapter {
        public static final String[] PERMIT_ALL = {
                /* ???????????????URL???????????? */
                "/",
                "/error",   // SpringBoot????????????????????????
                HomeController.URL_PATH_UNIFIED_ERROR,
                "/" + LoginController.URL_PATH_PREFIX + LoginController.URL_PATH_LOGIN_FORM,
                "/" + CreateAccountController.URL_PATH_PREFIX,
                "/" + CreateAccountController.URL_PATH_PREFIX + CreateAccountController.URL_PATH_CREATE
        };

        @Autowired
        CustomAuthenticationProvider authenticationProvider;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(authenticationProvider);
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers(IGNOREING_COMMON_MATCHERS);
        }

        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            System.out.println("UserSecurityConfiguration::configure(final HttpSecurity http)");

            http.antMatcher(LoginController.URL_PATH_AUTHORIZED_MATCHER + "/**")
                .authorizeRequests()
                    .anyRequest()
                        .hasRole("USER")

            .and().formLogin()
                    /* ???????????????????????????URL */
                    .loginProcessingUrl(LoginController.URL_PATH_LOGIN_PROCESSING_URL)
                    /* ????????????????????????????????????URL */
                    .loginPage("/" + LoginController.URL_PATH_PREFIX + LoginController.URL_PATH_LOGIN_FORM)
                    /* ?????????????????????????????????????????? */
                    .failureHandler(new CustomAuthenticationFailureHandler())
                    /* ??????????????????????????????????????????????????? */
                    .defaultSuccessUrl("/" + MyWorkController.URL_PATH_PREFIX + MyWorkController.URL_PATH_INDEX)
                    /* ??????????????????????????????????????? */
                    .usernameParameter("email").passwordParameter("password")

            .and().logout()
                    /* ????????????????????????URL */
                    .logoutUrl(LoginController.URL_PATH_LOGOUT)
                    /* ????????????????????????????????????????????????????????????????????? */
                    .logoutSuccessUrl("/" + LoginController.URL_PATH_PREFIX + LoginController.URL_PATH_LOGIN_FORM)

            .and().exceptionHandling()
                    // TODO: ???????????????????????????????????????????????????????????????
                    .accessDeniedPage(HomeController.URL_PATH_UNIFIED_ERROR)
            .and().csrf()
                    .csrfTokenRepository(getHttpSessionCsrfTokenRepositoryInstance())
                        .ignoringAntMatchers(LoginController.URL_PATH_LOGIN_PROCESSING_URL)
            .and().headers()
                    .frameOptions()
                        .sameOrigin()
            ;
        }
    }

    @Configuration
    @Order(1)
    public static class AdminSecurityConfiguration extends WebSecurityConfigurerAdapter {
        public static final String[] PERMIT_ALL = {
                /* ???????????????URL???????????? */
                AdmLoginController.URL_PATH_LOGOUT,
                "/" + AdmLoginController.URL_PATH_PREFIX + AdmLoginController.URL_PATH_LOGIN_FORM,
        };

        @Autowired
        AdminAuthenticationProvider authenticationProvider;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(authenticationProvider);
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers(IGNOREING_COMMON_MATCHERS);
        }

        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            System.out.println("AdminSecurityConfiguration::configure(final HttpSecurity http)");
            String h2ConsoleUrl = "/h2-console/**";
            String[] adminUrls = {
                    "/admin/**",
                    h2ConsoleUrl,
            };

            http.requestMatchers()
                    .antMatchers(adminUrls)
            .and().authorizeRequests()
                    .anyRequest()
                        .hasRole("ADMIN")

            // log in
            .and().formLogin()
                    /* ????????????????????????????????????URL */
                    .loginPage("/" + AdmLoginController.URL_PATH_PREFIX + AdmLoginController.URL_PATH_LOGIN_FORM)
                    /* ???????????????????????????URL */
                    .loginProcessingUrl(AdmLoginController.URL_PATH_LOGIN_PROCESSING_URL)
                    /* ?????????????????????????????????????????? */
                    .failureHandler(new AdminAuthenticationFailureHandler())
                    /* ??????????????????????????????????????????????????? */
                    .defaultSuccessUrl("/" + SystemConsoleController.URL_PATH_PREFIX + SystemConsoleController.URL_PATH_INDEX)
                    /* ??????????????????????????????????????? */
                    .usernameParameter("email").passwordParameter("password")

            // logout
            .and().logout()
                    /* ????????????????????????URL */
                    .logoutUrl(AdmLoginController.URL_PATH_LOGOUT)
                    /* ????????????????????????????????????????????????????????????????????? */
                    .logoutSuccessUrl("/" + AdmLoginController.URL_PATH_PREFIX + AdmLoginController.URL_PATH_LOGIN_FORM)

            .and().exceptionHandling()
                    // TODO: ???????????????????????????????????????????????????????????????
                    .accessDeniedPage(HomeController.URL_PATH_UNIFIED_ERROR)
            .and().csrf()
                    .csrfTokenRepository(getHttpSessionCsrfTokenRepositoryInstance())
                        .ignoringAntMatchers(
                                h2ConsoleUrl,
                                AdmLoginController.URL_PATH_LOGIN_PROCESSING_URL
                        )
            .and().headers()
                    .frameOptions()
                        .sameOrigin()
            ;
        }
    }

}
