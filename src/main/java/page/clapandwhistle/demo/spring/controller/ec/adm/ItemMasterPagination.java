package page.clapandwhistle.demo.spring.controller.ec.adm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import page.clapandwhistle.demo.spring.infrastructure.ec.TableModel.ItemMaster;
import page.clapandwhistle.demo.spring.infrastructure.ec.TableModel.ItemMasterRepository;

import java.util.Collections;
import java.util.List;

@Component
final public class ItemMasterPagination {
    private final ItemMasterRepository itemMasterRepos;

    public ItemMasterPagination(ItemMasterRepository itemMasterRepos) {
        this.itemMasterRepos = itemMasterRepos;
    }

    public List<ItemMaster> getList() {
        return itemMasterRepos.findAll();
    }

    public Page<ItemMaster> getPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPageNum = pageable.getPageNumber();
        int startItem = currentPageNum * pageSize;
        List<ItemMaster> dispItems, allItems = this.getList();

        if (allItems.size() < startItem) {
            dispItems = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, allItems.size());
            dispItems = allItems.subList(startItem, toIndex);
        }

        return new PageImpl<ItemMaster>(dispItems, PageRequest.of(currentPageNum, pageSize), allItems.size());
    }

}
