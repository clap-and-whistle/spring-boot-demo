package page.clapandwhistle.demo.spring.controller.open;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    public static final String URL_PATH_UNIFIED_ERROR = "/uni-err";
    private final String TEMPLATE_PATH_PREFIX = "open/home/";

    @RequestMapping("/")
    public String index() {
        return TEMPLATE_PATH_PREFIX + "index";
    }

    @RequestMapping(URL_PATH_UNIFIED_ERROR)
    public String error() {
        return TEMPLATE_PATH_PREFIX + "uni-err";
    }
}
