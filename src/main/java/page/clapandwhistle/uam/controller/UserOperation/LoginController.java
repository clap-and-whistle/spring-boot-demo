package page.clapandwhistle.uam.controller.UserOperation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import page.clapandwhistle.uam.controller.open.HomeController;
import page.clapandwhistle.uam.logics.UseCase.UserOperation.Login.Arguments;
import page.clapandwhistle.uam.logics.UseCase.UserOperation.Login.LoginUseCase;
import page.clapandwhistle.uam.logics.UseCase.UserOperation.Login.Result;

@Controller
public class LoginController {
    public static final String URL_PATH_PREFIX = "user-account/login";
    public static final String URL_PATH_LOGIN_FORM = "/input";
    public static final String URL_PATH_LOGIN_COMPLETE = "/complete";
    public static final String TEMPLATE_PATH_PREFIX = "user-operation/login/";

    final private LoginUseCase useCase;

    @Autowired
    public LoginController(LoginUseCase useCase) {
        this.useCase = useCase;
    }

    @RequestMapping(URL_PATH_PREFIX + URL_PATH_LOGIN_FORM)
    public String inputAction() {
        System.out.println("user-account::login::inputAction ");
        return TEMPLATE_PATH_PREFIX + "input";
    }

    @RequestMapping(URL_PATH_PREFIX + URL_PATH_LOGIN_COMPLETE)
    public String completeAction() {
        System.out.println("user-account::login::completeAction ");
        return TEMPLATE_PATH_PREFIX + "complete";
    }

    /**
     * 下記PRに記載した対応により、このアクションで行う想定だったユースケースの処理詳細は WebAuthSecurityConfiguration へ取って代われられてしまっている
     * @see https://github.com/clap-and-whistle/spring-boot-demo/pull/8
     *
     * TODO: Spring Security の認証処理を分解して、LoginUseCaseクラスの処理以後の部分だけをSpring Securityへ移譲するように改修したい
     */
    @PostMapping(URL_PATH_PREFIX)
    public String execAction(Model model,
            @RequestParam("email") Optional<String> email,
            @RequestParam("password") Optional<String> password) {
        String ret = "redirect:/";

        System.out.println("user-account::login::execAction, " + email.get() + ", " + password.get());
        try {
            Result result = useCase.execute(
                    new Arguments(
                        email.get()
                        , password.get()
                    ));
            ret = result.isSuccess()
                ? ret + URL_PATH_PREFIX + URL_PATH_LOGIN_COMPLETE
                : ret + URL_PATH_PREFIX + URL_PATH_LOGIN_FORM;
            System.out.println("user-account::login::execAction, result: " + (result.isSuccess() ? result.isSuccess() : result.eMessage()));
        } catch (Exception e) {
            ret = "redirect:" + HomeController.URL_PATH_UNIFIED_ERROR;
        }

        return ret;
    }

}
