package page.clapandwhistle.demo.spring.controller.ec.adm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import page.clapandwhistle.demo.spring.infrastructure.ec.TableModel.ItemMasterRepository;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ItemsMasterController {
    public static final String URL_PATH_PREFIX = "ec/adm/items-master";
    public static final String URL_PATH_NEW = "/new";
    public static final String URL_PATH_LIST = "/";

    private final String PAGE_TITLE = "商品マスタ管理";

    @Autowired
    private ItemMasterRepository itemMasterRepository;

    @GetMapping(URL_PATH_PREFIX + URL_PATH_LIST)
    public String indexAction(Model modelForTh) {
        System.out.println("ItemsMasterController::index: ");
        Map<String, String> links = new HashMap<>();
        links.put("." + URL_PATH_LIST, "商品一覧");
        links.put("." + URL_PATH_NEW, "商品登録");
        modelForTh.addAttribute("links", links);
        modelForTh.addAttribute("page_title", PAGE_TITLE);
        modelForTh.addAttribute("url_path_prefix", URL_PATH_PREFIX);
        modelForTh.addAttribute("items", itemMasterRepository.findAll());
        return "ec/adm/items-master/index";
    }

    @GetMapping(URL_PATH_PREFIX + URL_PATH_NEW)
    public String newAction() {
        System.out.println("ItemsMasterController::new: ");
        return "ec/adm/items-master/new";
    }

    @GetMapping(URL_PATH_PREFIX + "/{id}")
    public String showAction() {
        System.out.println("ItemsMasterController::show: ");
        return "ec/adm/items-master/show";
    }

    @GetMapping(URL_PATH_PREFIX + "/{id}/edit")
    public String editAction() {
        System.out.println("ItemsMasterController::edit: ");
        return "ec/adm/items-master/edit";
    }

    @PostMapping(URL_PATH_PREFIX)
    public String storeAction() {
        System.out.println("ItemsMasterController::store: ");
        return "redirect:/" + URL_PATH_PREFIX + URL_PATH_LIST;
    }

    @PutMapping(URL_PATH_PREFIX + "/{id}")
    public String updateAction() {
        System.out.println("ItemsMasterController::update: ");
        return "redirect:/" + URL_PATH_PREFIX + URL_PATH_LIST;
    }

    @DeleteMapping(URL_PATH_PREFIX + "/{id}")
    public String deleteAction() {
        System.out.println("ItemsMasterController::delete: ");
        return "redirect:/" + URL_PATH_PREFIX + URL_PATH_LIST;
    }
}
