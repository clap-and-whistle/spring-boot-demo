package page.clapandwhistle.demo.spring.controller.ec.adm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItemsMasterController {
    public static final String URL_PATH_PREFIX = "ec/adm/items-master";
    public static final String URL_PATH_LIST = "/";

    @GetMapping(URL_PATH_PREFIX + URL_PATH_LIST)
    public String indexAction() {
        return "ec/adm/items-master/index";
    }

}
