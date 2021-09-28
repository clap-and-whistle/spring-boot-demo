package page.clapandwhistle.demo.spring.controller.admin;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import page.clapandwhistle.demo.spring.controller.uam.SysAdminOperation.AdmLoginController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collection;

@Controller
public class SystemConsoleController {
    public static final String URL_PATH_PREFIX = "admin/sys-console";
    public static final String URL_PATH_INDEX = "/index";

    @RequestMapping(URL_PATH_PREFIX + URL_PATH_INDEX)
    public String index(Model model, Principal principal, HttpServletRequest request) {

        // 認証済みの Principal をコンソールログへ出力
        System.out.println("SystemConsoleController::index: name: " + principal.getName());
        System.out.println("SystemConsoleController::index: principal class: " + principal.getClass());

        // 付与されている Authority をコンソールログへ出力
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        System.out.println("MyWorkController::index: authorities: " + authorities.toString());

        // 生成されているCSRFトークンをコンソールログへ出力
        CsrfToken csrf = ((CsrfToken)request.getAttribute("_csrf"));
        System.out.println(csrf.getParameterName() + ": " + csrf.getToken());

        model.addAttribute("logoutUrl", AdmLoginController.URL_PATH_LOGOUT);
        return "admin/system-console/index";
    }

}
