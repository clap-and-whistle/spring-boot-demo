package page.clapandwhistle.demo.spring.controller.open;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import page.clapandwhistle.demo.spring.controller.ec.adm.ItemsMasterController;
import page.clapandwhistle.demo.spring.controller.admin.SystemConsoleController;
import page.clapandwhistle.demo.spring.controller.desk.MyWorkController;
import page.clapandwhistle.demo.spring.controller.uam.SysAdminOperation.AdmLoginController;
import page.clapandwhistle.demo.spring.controller.uam.UserOperation.CreateAccountController;
import page.clapandwhistle.demo.spring.controller.uam.UserOperation.LoginController;

@Controller
public class HomeController {
    public static final String URL_PATH_UNIFIED_ERROR = "/uni-err";

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute(
                "url_path_ec",
                ItemsMasterController.URL_PATH_PREFIX + ItemsMasterController.URL_PATH_LIST
        );

        String openIndexPath = "/"
                + PublicController.URL_PATH_PREFIX
                + PublicController.URL_PATH_INDEX;
        model.addAttribute("openIndexPath", openIndexPath);

        String userCreatePath = "/"
                + CreateAccountController.URL_PATH_PREFIX
                + CreateAccountController.URL_PATH_CREATE;
        model.addAttribute("userCreatePath", userCreatePath);

        String userLoginPath = "/"
                + LoginController.URL_PATH_PREFIX
                + LoginController.URL_PATH_LOGIN_FORM;
        model.addAttribute("userLoginPath", userLoginPath);

        String userMyDeskPath = "/"
                + MyWorkController.URL_PATH_PREFIX
                + MyWorkController.URL_PATH_INDEX;
        model.addAttribute("userMyDeskPath", userMyDeskPath);

        String adminLoginPath = "/"
                + AdmLoginController.URL_PATH_PREFIX
                + AdmLoginController.URL_PATH_LOGIN_FORM;
        model.addAttribute("adminLoginPath", adminLoginPath);

        String adminConsolePath = "/"
                + SystemConsoleController.URL_PATH_PREFIX
                + SystemConsoleController.URL_PATH_INDEX;
        model.addAttribute("adminConsolePath", adminConsolePath);

        return "open/home/index";
    }

    @RequestMapping(URL_PATH_UNIFIED_ERROR)
    public String error() {
        return "open/home/uni-err";
    }
}
