package page.clapandwhistle.demo.spring.controller.open;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PublicController {
    public static final String URL_PATH_PREFIX = "open";
    public static final String URL_PATH_INDEX = "/index";

    @RequestMapping(URL_PATH_PREFIX + URL_PATH_INDEX)
    public String index() {
        return "open/public/index";
    }

}
