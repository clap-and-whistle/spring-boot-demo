package page.clapandwhistle.demo.spring.controller.ec.adm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ItemsMasterControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemsMasterController targetController;

    @BeforeEach
    public void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(targetController).build();
    }

    @Test
    public void 初期表示画面へのGETリクエスト() throws Exception {
        System.out.println("Test: 初期表示画面へのGETリクエスト");
        mockMvc.perform(get("/" + ItemsMasterController.URL_PATH_PREFIX + ItemsMasterController.URL_PATH_LIST))
                .andExpect(status().isOk())
                .andExpect(view().name("ec/adm/items-master/index"));
    }

    @Test
    public void 商品新規登録画面へのGETリクエスト() throws Exception {
        System.out.println("Test: 商品新規登録画面へのGETリクエスト");
        mockMvc.perform(get("/" + ItemsMasterController.URL_PATH_PREFIX + ItemsMasterController.URL_PATH_NEW))
                .andExpect(status().isOk())
                .andExpect(view().name("ec/adm/items-master/new"));
    }

    @Test
    public void 商品詳細画面へのGETリクエスト() throws Exception {
        System.out.println("Test: 商品詳細画面へのGETリクエスト");
        mockMvc.perform(get("/" + ItemsMasterController.URL_PATH_PREFIX + "/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("ec/adm/items-master/show"));
    }

    @Test
    public void 商品編集画面へのGETリクエスト() throws Exception {
        System.out.println("Test: 商品編集画面へのGETリクエスト");
        mockMvc.perform(get("/" + ItemsMasterController.URL_PATH_PREFIX + "/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("ec/adm/items-master/edit"));
    }

    @Test
    public void 商品新規登録のPOSTリクエスト() throws Exception {
        System.out.println("Test: 商品新規登録のPOSTリクエスト");
        mockMvc.perform(post("/" + ItemsMasterController.URL_PATH_PREFIX)
                    .param("name", "test")
                    .param("price", "3000.0")
                    .param("vendor", "test Inc.")
                )
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void 指定商品への更新POSTリクエスト() throws Exception {
        System.out.println("Test: 指定商品への更新POSTリクエスト");
        mockMvc.perform(post("/" + ItemsMasterController.URL_PATH_PREFIX + "/1")
                    .param("name", "test")
                    .param("price", "3000.0")
                    .param("vendor", "test Inc.")
                )
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void 指定商品への削除POSTリクエスト() throws Exception {
        System.out.println("Test: 指定商品への削除POSTリクエスト");
        int targetId = 6;
        mockMvc.perform(post("/" + ItemsMasterController.URL_PATH_PREFIX + "/" + targetId + "/delete")
                    .param("_method", "delete")
                )
                .andExpect(status().is3xxRedirection());
    }

}
