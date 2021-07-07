package page.clapandwhistle.demo.spring.controller.open;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    public static final String URL_PATH_UNIFIED_ERROR = "/uni-err";

    @RequestMapping("/")
    public String index() {
        return "open/home/index";
    }

    @RequestMapping(URL_PATH_UNIFIED_ERROR)
    public String error() {
        return "open/home/uni-err";
    }
}
