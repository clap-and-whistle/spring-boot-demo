package page.clapandwhistle.demo.spring.controller.desk;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import page.clapandwhistle.demo.spring.controller.uam.UserOperation.LoginController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collection;

@Controller
public class MyWorkController {
    public static final String URL_PATH_PREFIX = "desk";
    public static final String URL_PATH_INDEX = "/index";
    private final String TEMPLATE_PATH_PREFIX = "desk/my-work/";

    @RequestMapping(URL_PATH_PREFIX + URL_PATH_INDEX)
    public String index(Model model, Principal principal, HttpServletRequest request) {
        System.out.println("MyWorkController::index: name: " + principal.getName());
        System.out.println("MyWorkController::index: class: " + principal.getClass());

        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)    SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        System.out.println("MyWorkController::index: authorities: " + authorities.toString());

        CsrfToken csrf = ((CsrfToken)request.getAttribute("_csrf"));
        System.out.println(csrf.getParameterName());
        System.out.println(csrf.getToken());

        model.addAttribute("logoutUrl", LoginController.URL_PATH_LOGOUT);
        return TEMPLATE_PATH_PREFIX + "index";
    }

}
