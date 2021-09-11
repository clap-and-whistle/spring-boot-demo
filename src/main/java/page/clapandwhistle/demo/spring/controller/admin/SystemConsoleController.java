package page.clapandwhistle.demo.spring.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import page.clapandwhistle.demo.spring.controller.uam.SysAdminOperation.AdmLoginController;

import java.security.Principal;

@Controller
public class SystemConsoleController {
    public static final String URL_PATH_PREFIX = "admin/sys-console";
    public static final String URL_PATH_INDEX = "/index";
    private final String TEMPLATE_PATH_PREFIX = "admin/system-console/index";

    @RequestMapping(URL_PATH_PREFIX + URL_PATH_INDEX)
    public String index(Model model, Principal principal) {
        System.out.println("SystemConsoleController::index: name: " + principal.getName());
        System.out.println("SystemConsoleController::index: class: " + principal.getClass());
        model.addAttribute("logoutUrl", AdmLoginController.URL_PATH_LOGOUT);
        return "admin/system-console/index";
    }

}
