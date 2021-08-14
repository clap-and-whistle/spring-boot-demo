package page.clapandwhistle.demo.spring.controller.open;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import page.clapandwhistle.demo.spring.bizlogic.experimental.BookForSale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
    public String index(
        Model model,
        @RequestParam("page") Optional<Integer> page,
        @RequestParam("size") Optional<Integer> size
    ) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<BookForSale> bookPage = findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("bookPage", bookPage);

        int totalPages = bookPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "open/paging-test/index";
    }

    public Page<BookForSale> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<BookForSale> list;

        if (books.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, books.size());
            list = books.subList(startItem, toIndex);
        }

        Page<BookForSale> bookPage = new PageImpl<BookForSale>(list, PageRequest.of(currentPage, pageSize), books.size());

        return bookPage;
    }

}
