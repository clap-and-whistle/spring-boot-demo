package page.clapandwhistle.demo.spring.controller.open;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import page.clapandwhistle.demo.spring.controller.ec.adm.ItemsMasterController;

@Controller
public class HomeController {
    public static final String URL_PATH_UNIFIED_ERROR = "/uni-err";

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute(
                "url_path_ec",
                ItemsMasterController.URL_PATH_PREFIX + ItemsMasterController.URL_PATH_LIST
        );
        return "open/home/index";
    }

    @RequestMapping(URL_PATH_UNIFIED_ERROR)
    public String error() {
        return "open/home/uni-err";
    }
}
