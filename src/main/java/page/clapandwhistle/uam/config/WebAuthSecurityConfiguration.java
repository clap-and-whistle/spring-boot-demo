package page.clapandwhistle.uam.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import page.clapandwhistle.uam.controller.UserOperation.CreateAccountController;
import page.clapandwhistle.uam.controller.UserOperation.LoginController;
import page.clapandwhistle.uam.controller.desk.MyWorkController;
import page.clapandwhistle.uam.controller.open.HomeController;
import page.clapandwhistle.uam.framework.security.AuthUserDetailsService;
import page.clapandwhistle.uam.framework.security.SpringAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class WebAuthSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    AuthUserDetailsService userDetailsService;

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/css/**",
                "/js/**",
                "/open/**"
        );
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers(
                    /* 認証不要なURLのリスト */
                    "/",
                    "/error",   // SpringBoot標準のエラー画面
                    HomeController.URL_PATH_UNIFIED_ERROR,
                    "/" + LoginController.URL_PATH_PREFIX + LoginController.URL_PATH_LOGIN_FORM,
                    "/" + CreateAccountController.URL_PATH_PREFIX,
                    "/" + CreateAccountController.URL_PATH_PREFIX + CreateAccountController.URL_PATH_CREATE
                ).permitAll()
            /* それ以外はすべて認証を必要とする */
            .anyRequest().authenticated();

        http.formLogin()
            /* ログイン処理を行うURL */
            .loginProcessingUrl("/" + LoginController.URL_PATH_PREFIX)
            /* ログイン情報の入力を行うURL */
            .loginPage("/" + LoginController.URL_PATH_PREFIX + LoginController.URL_PATH_LOGIN_FORM)
            /* 認証失敗時のハンドラをセット */
            .failureHandler(new SpringAuthenticationFailureHandler())
            /* 認証成功時のデフォルトのジャンプ先 */
            .defaultSuccessUrl("/" + MyWorkController.URL_PATH_PREFIX + MyWorkController.URL_PATH_INDEX)
            /* 認証に使用する入力値セット */
            .usernameParameter("email").passwordParameter("password")
            .and();
    }
}
