package page.clapandwhistle.demo.spring.controller.desk;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class MyWorkController {
    public static final String URL_PATH_PREFIX = "desk";
    public static final String URL_PATH_INDEX = "/index";
    private final String TEMPLATE_PATH_PREFIX = "desk/my-work/";

    @RequestMapping(URL_PATH_PREFIX + URL_PATH_INDEX)
    public String index(Principal principal) {
        System.out.println("MyWorkController::index: name: " + principal.getName());
        System.out.println("MyWorkController::index: class: " + principal.getClass());
        return TEMPLATE_PATH_PREFIX + "index";
    }

}
