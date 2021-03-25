package page.clapandwhistle.uam.controller.UserOperation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import page.clapandwhistle.uam.logics.UseCase.UserOperation.Login.Arguments;
import page.clapandwhistle.uam.logics.UseCase.UserOperation.Login.LoginUseCase;
import page.clapandwhistle.uam.logics.UseCase.UserOperation.Login.Result;

@Controller
public class LoginController {
    private final String URL_PATH_PREFIX = "user-account";
    private final String TEMPLATE_PATH_PREFIX = "user-account";

    final private LoginUseCase useCase;

    @Autowired
    public LoginController(LoginUseCase useCase) {
        this.useCase = useCase;
    }

    @RequestMapping(URL_PATH_PREFIX + "/login")
    public String inputAction() {
        System.out.println("user-account::login::inputAction ");
        return TEMPLATE_PATH_PREFIX + "/login";
    }

    @RequestMapping(URL_PATH_PREFIX + "/login-complete")
    public String completeAction() {
        System.out.println("user-account::login::completeAction ");
        return TEMPLATE_PATH_PREFIX + "/login-complete";
    }

    @PostMapping(URL_PATH_PREFIX + "/login")
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
                ? "redirect:/" + TEMPLATE_PATH_PREFIX + "/login-complete"
                : TEMPLATE_PATH_PREFIX + "/login";
            System.out.println("user-account::login::execAction, result: " + (result.isSuccess() ? result.isSuccess() : result.eMessage()));
        } catch (Exception e) {
            ret = "redirect:/e";
        }

        return ret;
    }

}
