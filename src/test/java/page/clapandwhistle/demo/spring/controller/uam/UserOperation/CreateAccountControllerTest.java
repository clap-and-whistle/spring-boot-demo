package page.clapandwhistle.demo.spring.controller.uam.UserOperation;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import unit.infrastructure.uam.AggregateRepository.User.ForTestUserAggregateRepository;

import javax.servlet.ServletContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@WebAppConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CreateAccountControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private ForTestUserAggregateRepository forTestUserRepos;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        this.forTestUserRepos = ForTestUserAggregateRepository.getInstance();
    }

    /**
     * 下記コメント文の引用元は次のリンク先です: {@link <a href="https://www.baeldung.com/integration-testing-in-spring#4-verify-test-configuration">Integration Testing in Spring | Baeldung</a>}
     *  > ### Verify Test Configuration ###
     *  > Let's verify that we're loading the WebApplicationContext object (webApplicationContext) properly. We'll also check that the right servletContext is being attached:
     *  > Notice that we're also checking that a GreetController.java bean exists in the web context. This ensures that Spring beans are loaded properly. At this point, the setup of the integration test is done. Now, let's see how we can test resource methods using the MockMvc object.
     */
    @Test
    @Order(1)
    public void 事前準備_適切なservletContextがアタッチされていることも確認する() {
        // WebApplicationContextオブジェクト（webApplicationContext）が正しくロードされていることを確認
        ServletContext servletContext = webApplicationContext.getServletContext();
        Assertions.assertNotNull(servletContext);
        Assertions.assertTrue(servletContext instanceof MockServletContext);

        // 意図したservletContextがアタッチされていることも確認
        Assertions.assertNotNull(webApplicationContext.getBean("createAccountController"));

        // なお、上記の getBean() で取得することのできるBean定義名のリストは次のようにして調べることができる
        // System.out.println(Arrays.toString(webApplicationContext.getBeanDefinitionNames()));
    }

    @Test
    @Order(2)
    public void 入力画面を要求() throws Exception {
        mockMvc.perform(get("/" + CreateAccountController.URL_PATH_PREFIX + CreateAccountController.URL_PATH_CREATE))
                .andDo(print())
                .andExpect(status().isOk())    // 200 OK
                .andExpect(view().name("uam/user-operation/create-account/new"));
    }

    @Test
    @Order(3)
    public void 基本コース1_POST結果をテスト_emailとpasswordのみ() throws Exception {
        MultiValueMap<String, String> postParams = new LinkedMultiValueMap<>();
        postParams.add("email", forTestUserRepos.使われていないメールアドレスを生成する());
        postParams.add("password", ForTestUserAggregateRepository.テスト用Password);
        mockMvc.perform(post("/" + CreateAccountController.URL_PATH_PREFIX)
                        .params(postParams)
                    )
                .andDo(print())
                .andExpect(status().is3xxRedirection())    // 302 Redirect
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @Order(4)
    public void 基本コース2_POST結果をテスト_フルオプション() throws Exception {
        MultiValueMap<String, String> postParams = new LinkedMultiValueMap<>();
        postParams.add("email", forTestUserRepos.使われていないメールアドレスを生成する());
        postParams.add("password", ForTestUserAggregateRepository.テスト用Password);
        postParams.add("full_name", "テスト太郎");
        postParams.add("birth_date_str", "20000229");
        mockMvc.perform(post("/" + CreateAccountController.URL_PATH_PREFIX)
                        .params(postParams)
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())    // 302 Redirect
                .andExpect(redirectedUrl("/"));

        // TODO: issue#3 の実装時に、full_name と birth_date_str が正しくデータソースへ登録されたかどうかのAssertionを追記するべし
    }

}
