package page.clapandwhistle.demo.spring.controller.desk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.User.User;
import page.clapandwhistle.demo.spring.framework.security.WithMockCustomUser;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MyWorkControllerTest {
    final public static long テスト用の有効なユーザID = 1;
    final public static String テスト用の有効なメールアドレス = "hoge01@example.local";

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void beforeEach() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }

    @Test
    @WithMockCustomUser(
            id = テスト用の有効なユーザID,
            email = テスト用の有効なメールアドレス,
            role = User.ROLE
    )
    public void getIndexTest() throws Exception {
        String index = "/" + MyWorkController.URL_PATH_PREFIX + MyWorkController.URL_PATH_INDEX;
        String responseContents = mockMvc
                .perform(MockMvcRequestBuilders.get(index))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();

        assertThat(responseContents).contains(テスト用の有効なメールアドレス);
    }
}
