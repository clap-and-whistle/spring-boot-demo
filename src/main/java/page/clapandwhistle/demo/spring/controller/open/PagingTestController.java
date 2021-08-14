package page.clapandwhistle.demo.spring.controller.open;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public final class PagingTestController {

    @RequestMapping("/open/page/")
    public String index() {
        return "open/paging-test/index";
    }


}
