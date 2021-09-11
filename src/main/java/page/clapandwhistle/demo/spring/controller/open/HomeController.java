package page.clapandwhistle.demo.spring.controller.open;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import page.clapandwhistle.demo.spring.controller.uam.SysAdminOperation.AdmLoginController;

@Controller
public class HomeController {
    public static final String URL_PATH_UNIFIED_ERROR = "/uni-err";
    private String adminLoginPath
        = "/"
        + AdmLoginController.URL_PATH_PREFIX
        + AdmLoginController.URL_PATH_LOGIN_FORM;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("adminLoginPath", adminLoginPath);
        return "open/home/index";
    }

    @RequestMapping(URL_PATH_UNIFIED_ERROR)
    public String error() {
        return "open/home/uni-err";
    }
}
