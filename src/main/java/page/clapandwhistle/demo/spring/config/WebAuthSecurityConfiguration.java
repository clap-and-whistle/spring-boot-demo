package page.clapandwhistle.demo.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import page.clapandwhistle.demo.spring.controller.desk.MyWorkController;
import page.clapandwhistle.demo.spring.controller.open.HomeController;
import page.clapandwhistle.demo.spring.controller.uam.UserOperation.CreateAccountController;
import page.clapandwhistle.demo.spring.controller.uam.UserOperation.LoginController;
import page.clapandwhistle.demo.spring.framework.security.CustomAuthenticationProvider;
import page.clapandwhistle.demo.spring.framework.security.SpringAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class WebAuthSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    CustomAuthenticationProvider authenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
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
            .antMatchers("/h2-console/**")
                .hasRole("ADMIN")
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
            .anyRequest().authenticated()
            /* h2-consoleについての許可をいくつか */
            .and().csrf().ignoringAntMatchers("/h2-console/**")
            .and().headers().frameOptions().sameOrigin();
;

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
            .usernameParameter("email").passwordParameter("password");

        http.logout()
            /* ログアウト処理のURL */
            .logoutRequestMatcher(new AntPathRequestMatcher(LoginController.URL_PATH_LOGOUT + "/**"))
            /* ログアウト完了後は、ひとまずログイン画面を表示 */
            .logoutSuccessUrl("/" + LoginController.URL_PATH_PREFIX + LoginController.URL_PATH_LOGIN_FORM);
    }
}
