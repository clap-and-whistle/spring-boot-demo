package page.clapandwhistle.demo.spring.bizlogic.experimental;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public final class BookForSaleService {

    final private List<BookForSale> books = new ArrayList<BookForSale>();

    public BookForSaleService() {
        int idx;
        for (int i = 0; i < 20; i++) {
            idx = i + 1;
            books.add(
                new BookForSale(
                    idx,
                    "○○○○ △△△△△△△ □□□□□ に詳しい本-" + String.format("%06d", idx),
                    "著者 AAAAAA BBBBB-" + idx,
                    BigDecimal.valueOf(1000)
                )
            );
        }
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
