package page.clapandwhistle.demo.spring.controller.ec.adm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import page.clapandwhistle.demo.spring.bizlogic.ec.Aggregate.ItemBasicInfo;
import page.clapandwhistle.demo.spring.bizlogic.ec.UseCase.Adm.AddItemBasicInfoUseCase;
import page.clapandwhistle.demo.spring.bizlogic.ec.UseCase.Adm.UpdateItemBasicInfoUseCase;
import page.clapandwhistle.demo.spring.infrastructure.ec.TableModel.ItemMaster;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ItemsMasterController {
    public static final String URL_PATH_PREFIX = "ec/adm/items-master";
    public static final String URL_PATH_NEW = "/new";
    public static final String URL_PATH_LIST = "/";

    private final String PAGE_TITLE = "商品マスタ管理";

    private final ItemMasterPagination itemsPagination;
    private final AddItemBasicInfoUseCase addUseCase;
    private final UpdateItemBasicInfoUseCase updateUseCase;

    @Autowired
    public ItemsMasterController(
            ItemMasterPagination itemsPagination,
            AddItemBasicInfoUseCase addUseCase,
            UpdateItemBasicInfoUseCase updateUseCase) {
        this.itemsPagination = itemsPagination;
        this.addUseCase = addUseCase;
        this.updateUseCase = updateUseCase;
    }

    @GetMapping(URL_PATH_PREFIX + URL_PATH_LIST)
    public String indexAction(
            Model modelForTh,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size
    ) {
        System.out.println("ItemsMasterController::index: ");

        Map<String, String> links = new HashMap<>();
        links.put("." + URL_PATH_LIST, "商品一覧");
        links.put("." + URL_PATH_NEW, "商品登録");
        modelForTh.addAttribute("links", links);
        modelForTh.addAttribute("page_title", PAGE_TITLE);
        modelForTh.addAttribute("url_path_prefix", URL_PATH_PREFIX);

        // ページング用の処理
        final int currentPage = page.orElse(1);
        final int pageSize = size.orElse(5);

        Page<ItemMaster> itemsPage = itemsPagination.getPaginated(PageRequest.of(currentPage - 1, pageSize));
        modelForTh.addAttribute("itemsPage", itemsPage);

        int totalPages = itemsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelForTh.addAttribute("pageNumbers", pageNumbers);
        }

        return "ec/adm/items-master/index";
    }

    @GetMapping(URL_PATH_PREFIX + URL_PATH_NEW)
    public String newAction(Model modelForTh) {
        System.out.println("ItemsMasterController::new: ");
        Map<String, String> links = new HashMap<>();
        links.put("." + URL_PATH_LIST, "商品一覧");
        modelForTh.addAttribute("links", links);

        modelForTh.addAttribute("item", new ItemBasicInfo((long) 0, "", 0, ""));
        modelForTh.addAttribute("page_title", PAGE_TITLE);
        modelForTh.addAttribute("form_action", "/" + URL_PATH_PREFIX);
        return "ec/adm/items-master/new";
    }

    @PostMapping(URL_PATH_PREFIX)
    public String storeAction(@ModelAttribute("item") @Validated ItemBasicInfo item, BindingResult result) {
        System.out.println("ItemsMasterController::store: ");
        System.out.println("ItemsMasterController::create: name: " + item.name());
        System.out.println("ItemsMasterController::create: price: " + item.price());
        System.out.println("ItemsMasterController::create: vendor" + item.vendor());
        if (result.hasErrors()) {
            System.out.println("ItemsMasterController::store: error: " + result.getErrorCount());
            return "redirect:/" + URL_PATH_PREFIX + URL_PATH_NEW;
        } else {
            this.addUseCase.execute(item.name(), item.price(), item.vendor());
            return "redirect:/" + URL_PATH_PREFIX + URL_PATH_LIST;
        }
    }

    @GetMapping(URL_PATH_PREFIX + "/{id}")
    public String showAction(@PathVariable Long id, Model modelForTh) {
        System.out.println("ItemsMasterController::show: ");
        Map<String, String> links = new HashMap<>();
        links.put("." + URL_PATH_LIST, "商品一覧");
        links.put("." + URL_PATH_NEW, "商品登録");
        modelForTh.addAttribute("links", links);

        modelForTh.addAttribute("item", this.itemsPagination.get(id));
        modelForTh.addAttribute("page_title", PAGE_TITLE);
        modelForTh.addAttribute("url_path_edit", "./" + id + "/edit");
        return "ec/adm/items-master/show";
    }

    @GetMapping(URL_PATH_PREFIX + "/{id}/edit")
    public String editAction(@PathVariable("id") Long id, Model modelForTh) {
        System.out.println("ItemsMasterController::edit: id: " + id);

        Map<String, String> links = new HashMap<>();
        links.put(".." + URL_PATH_LIST, "商品一覧");
        links.put(".." + URL_PATH_NEW, "商品登録");
        modelForTh.addAttribute("links", links);
        modelForTh.addAttribute("page_title", PAGE_TITLE);

        modelForTh.addAttribute("item", this.itemsPagination.get(id));
        modelForTh.addAttribute("page_title", PAGE_TITLE);
        modelForTh.addAttribute("form_action", "../" + id);
        return "ec/adm/items-master/edit";
    }

    @PostMapping(URL_PATH_PREFIX + "/{id}")
    public String updateAction(@PathVariable("id") Long id, @ModelAttribute("item") @Validated ItemBasicInfo item, BindingResult result) {
        System.out.println("ItemsMasterController::update: id: " + id);
        System.out.println("ItemsMasterController::create: name: " + item.name());
        System.out.println("ItemsMasterController::create: price: " + item.price());
        System.out.println("ItemsMasterController::create: vendor" + item.vendor());
        if (result.hasErrors()) {
            System.out.println("ItemsMasterController::update: error: " + result.getErrorCount());
            return "redirect:/" + URL_PATH_PREFIX + "/" + id + "/edit";
        } else {
            this.updateUseCase.execute(id, item.name(), item.price(), item.vendor());
            return "redirect:/" + URL_PATH_PREFIX + URL_PATH_LIST;
        }
    }

    @DeleteMapping(URL_PATH_PREFIX + "/{id}")
    public String deleteAction() {
        System.out.println("ItemsMasterController::delete: ");
        return "redirect:/" + URL_PATH_PREFIX + URL_PATH_LIST;
    }
}
