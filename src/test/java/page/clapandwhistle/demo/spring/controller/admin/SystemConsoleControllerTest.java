package page.clapandwhistle.demo.spring.controller.admin;

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
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.AdminUser.AdminUser;
import page.clapandwhistle.demo.spring.framework.security.WithMockCustomUser;
import unit.infrastructure.uam.AggregateRepository.AdminUser.ForTestAdminUserAggregateRepository;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
final public class SystemConsoleControllerTest {

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
            id = ForTestAdminUserAggregateRepository.テスト用の有効な管理者ID,
            email = ForTestAdminUserAggregateRepository.テスト用の有効な管理者メールアドレス,
            role = AdminUser.ROLE
    )
    public void getIndexTest() throws Exception {
        String index = "/" + SystemConsoleController.URL_PATH_PREFIX + SystemConsoleController.URL_PATH_INDEX;
        String responseContents = mockMvc
                .perform(MockMvcRequestBuilders.get(index))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();

        assertThat(responseContents).contains(ForTestAdminUserAggregateRepository.テスト用の有効な管理者メールアドレス);
    }
}
