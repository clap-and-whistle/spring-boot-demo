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
                /* 認証不要なURLのリスト */
                "/",
                "/error",   // SpringBoot標準のエラー画面
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
                    /* ログイン処理を行うURL */
                    .loginProcessingUrl(LoginController.URL_PATH_LOGIN_PROCESSING_URL)
                    /* ログイン情報の入力を行うURL */
                    .loginPage("/" + LoginController.URL_PATH_PREFIX + LoginController.URL_PATH_LOGIN_FORM)
                    /* 認証失敗時のハンドラをセット */
                    .failureHandler(new CustomAuthenticationFailureHandler())
                    /* 認証成功時のデフォルトのジャンプ先 */
                    .defaultSuccessUrl("/" + MyWorkController.URL_PATH_PREFIX + MyWorkController.URL_PATH_INDEX)
                    /* 認証に使用する入力値セット */
                    .usernameParameter("email").passwordParameter("password")

            .and().logout()
                    /* ログアウト処理のURL */
                    .logoutUrl(LoginController.URL_PATH_LOGOUT)
                    /* ログアウト完了後は、ひとまずログイン画面を表示 */
                    .logoutSuccessUrl("/" + LoginController.URL_PATH_PREFIX + LoginController.URL_PATH_LOGIN_FORM)

            .and().exceptionHandling()
                    // TODO: 現在は暫定値。適切なページを作成し設定する
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
                /* 認証不要なURLのリスト */
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
                    /* ログイン情報の入力を行うURL */
                    .loginPage("/" + AdmLoginController.URL_PATH_PREFIX + AdmLoginController.URL_PATH_LOGIN_FORM)
                    /* ログイン処理を行うURL */
                    .loginProcessingUrl(AdmLoginController.URL_PATH_LOGIN_PROCESSING_URL)
                    /* 認証失敗時のハンドラをセット */
                    .failureHandler(new AdminAuthenticationFailureHandler())
                    /* 認証成功時のデフォルトのジャンプ先 */
                    .defaultSuccessUrl("/" + SystemConsoleController.URL_PATH_PREFIX + SystemConsoleController.URL_PATH_INDEX)
                    /* 認証に使用する入力値セット */
                    .usernameParameter("email").passwordParameter("password")

            // logout
            .and().logout()
                    /* ログアウト処理のURL */
                    .logoutUrl(AdmLoginController.URL_PATH_LOGOUT)
                    /* ログアウト完了後は、ひとまずログイン画面を表示 */
                    .logoutSuccessUrl("/" + AdmLoginController.URL_PATH_PREFIX + AdmLoginController.URL_PATH_LOGIN_FORM)

            .and().exceptionHandling()
                    // TODO: 現在は暫定値。適切なページを作成し設定する
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
