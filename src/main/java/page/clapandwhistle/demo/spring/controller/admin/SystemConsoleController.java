package page.clapandwhistle.demo.spring.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.AdminUser.AdminUser;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.AdminUserAggregateRepositoryInterface;
import page.clapandwhistle.demo.spring.config.ExternalSettings;
import page.clapandwhistle.demo.spring.controller.uam.SysAdminOperation.AdmLoginController;
import page.clapandwhistle.demo.spring.framework.security.DefaultAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collection;

@Controller
final public class SystemConsoleController {
    public static final String URL_PATH_PREFIX = "admin/sys-console";
    public static final String URL_PATH_INDEX = "/index";

    final private AdminUserAggregateRepositoryInterface adminUserRepos;
    final private ExternalSettings extSettings;

    @Autowired
    public SystemConsoleController(AdminUserAggregateRepositoryInterface adminUserRepos, ExternalSettings extSettings) {
        this.adminUserRepos = adminUserRepos;
        this.extSettings = extSettings;
    }

    @RequestMapping(URL_PATH_PREFIX + URL_PATH_INDEX)
    public String index(Model model, Principal principal, HttpServletRequest request) {
        DefaultAuthenticationToken token = (DefaultAuthenticationToken)principal;

        // Debug用
        model.addAttribute("h2ConsoleAvailable",
                this.extSettings.getMvnProfile().equals("default")
                    && token.getAuthorities().contains(new SimpleGrantedAuthority(AdminUser.ROLE))
        );

        // 認証済みの Principal をコンソールログへ出力
        System.out.println("SystemConsoleController::index: principal class: " + principal.getClass().getSimpleName() + ": (Package:" + principal.getClass().getPackageName() + ")");
        System.out.println("SystemConsoleController::index: token.getName(): " + token.getName());

        // 付与されている Authority をコンソールログへ出力
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        System.out.println("SystemConsoleController::index: authorities: " + authorities.toString());

        // 生成されているCSRFトークンをコンソールログへ出力
        CsrfToken csrf = ((CsrfToken)request.getAttribute("_csrf"));
        System.out.println(csrf.getParameterName() + ": " + csrf.getToken());

        // AdminUser集約の構築
        AdminUser adminUserAggregate = this.adminUserRepos.findById(token.getPrincipalId());

        model.addAttribute("loginUserEmail", adminUserAggregate.email());
        model.addAttribute("logoutUrl", AdmLoginController.URL_PATH_LOGOUT);
        return "admin/system-console/index";
    }

}
