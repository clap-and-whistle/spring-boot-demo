package page.clapandwhistle.demo.spring.controller.opem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import page.clapandwhistle.demo.spring.controller.ec.adm.ItemsMasterController;
import page.clapandwhistle.demo.spring.controller.open.HomeController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HomeController targetController;

    @BeforeEach
    public void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(targetController).build();
    }

    @Test
    public void getIndexTest() throws Exception {
        mockMvc.perform(get("/"))
            .andExpect(status().isOk())    // 200 OK
            .andExpect(view().name("open/home/index"))
            .andExpect(model().attribute(
                "url_path_ec",
                ItemsMasterController.URL_PATH_PREFIX + ItemsMasterController.URL_PATH_LIST
            ));
    }
}
