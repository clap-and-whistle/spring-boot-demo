package page.clapandwhistle.demo.spring.controller.desk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.User.User;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.UserAggregateRepositoryInterface;
import page.clapandwhistle.demo.spring.controller.uam.UserOperation.LoginController;
import page.clapandwhistle.demo.spring.framework.security.DefaultAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collection;

@Controller
final public class MyWorkController {
    public static final String URL_PATH_PREFIX = "desk";
    public static final String URL_PATH_INDEX = "/index";

    final private UserAggregateRepositoryInterface userRepos;

    @Autowired
    public MyWorkController(UserAggregateRepositoryInterface userRepos) {
        this.userRepos = userRepos;
    }

    @RequestMapping(URL_PATH_PREFIX + URL_PATH_INDEX)
    public String index(Model model, Principal principal, HttpServletRequest request) {
        DefaultAuthenticationToken token = (DefaultAuthenticationToken)principal;

        // 認証済みの Principal をコンソールログへ出力
        System.out.println("MyWorkController::index: principal class: " + principal.getClass().getSimpleName() + ": (Package:" + principal.getClass().getPackageName() + ")");
        System.out.println("MyWorkController::index: name: " + principal.getName());

        // 付与されている Authority をコンソールログへ出力
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        System.out.println("MyWorkController::index: authorities: " + authorities.toString());

        // 生成されているCSRFトークンをコンソールログへ出力
        CsrfToken csrf = ((CsrfToken)request.getAttribute("_csrf"));
        System.out.println(csrf.getParameterName() + ": " + csrf.getToken());

        // User集約の構築
        User userAggregate = this.userRepos.findById(token.getPrincipalId());

        model.addAttribute("loginUserEmail", userAggregate.email());
        model.addAttribute("logoutUrl", LoginController.URL_PATH_LOGOUT);
        return "desk/my-work/index";
    }

}
