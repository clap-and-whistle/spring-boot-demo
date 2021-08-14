package page.clapandwhistle.demo.spring.controller.open;

import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import page.clapandwhistle.demo.spring.bizlogic.experimental.BookForSale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public final class PagingTestController {

    final private List<BookForSale> books = new ArrayList<BookForSale>();

    public PagingTestController() {
        int idx;
        for (int i = 0; i < 20; i++) {
            idx = i + 1;
            books.add(new BookForSale(idx, "○○○○ △△△△△△△ □□□□□ に詳しい本-" + String.format("%06d", idx)));
        }
    }

    @RequestMapping(value = "/open/page/", method = RequestMethod.GET)
    public String index(Model model) {
        /* ひとまず表示をテキトーな表示を行うための暫定値だけ設定しておく */
        int currentPage = 1;
        int pageSize = 5;
        List<Integer> pageNumbers =
                IntStream.rangeClosed(1, (20 / pageSize) + 1).boxed().collect(Collectors.toList());

        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("bookPage", new PageImpl<BookForSale>(Collections.emptyList()));

        return "open/paging-test/index";
    }


}
