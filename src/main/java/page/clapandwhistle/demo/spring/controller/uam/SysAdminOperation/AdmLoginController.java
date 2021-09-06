package page.clapandwhistle.demo.spring.controller.uam.SysAdminOperation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdmLoginController {
    public static final String URL_PATH_AUTHORIZED_MATCHER = "/admin";
    public static final String URL_PATH_LOGIN_PROCESSING_URL = URL_PATH_AUTHORIZED_MATCHER + "/login";
    public static final String URL_PATH_LOGOUT = URL_PATH_AUTHORIZED_MATCHER + "/logout";

    public static final String URL_PATH_PREFIX = "admin-account/login";
    public static final String URL_PATH_LOGIN_FORM = "/input";

    @RequestMapping(URL_PATH_PREFIX + URL_PATH_LOGIN_FORM)
    public String inputAction(Model model) {
        System.out.println("adimin-account::login::inputAction ");

        model.addAttribute("loginProcessingUrl", URL_PATH_LOGIN_PROCESSING_URL);
        return "uam/admin-operation/login/input";
    }

}
