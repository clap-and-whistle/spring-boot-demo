package page.clapandwhistle.demo.spring.controller.uam.UserOperation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    public static final String URL_PATH_PREFIX = "user-account/login";
    public static final String URL_PATH_LOGIN_FORM = "/input";
    public static final String URL_PATH_LOGOUT = "/logout";
    public static final String TEMPLATE_PATH_PREFIX = "uam/user-operation/login/";

    @RequestMapping(URL_PATH_PREFIX + URL_PATH_LOGIN_FORM)
    public String inputAction() {
        System.out.println("user-account::login::inputAction ");
        return TEMPLATE_PATH_PREFIX + "input";
    }

}
