package unit.bizlogic.uam.UseCase.SysAdminOperation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import page.clapandwhistle.demo.spring.bizlogic.uam.UseCase.SysAdminOperation.Login.LoginUseCase;
import page.clapandwhistle.demo.spring.bizlogic.uam.UseCase.SysAdminOperation.Login.Result;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.AdminUser.Exception.NotExistException;
import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.AdminUser.Exception.PasswordIsNotMatchException;
import unit.infrastructure.uam.AggregateRepository.AdminUser.ForTestAdminUserAggregateRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


final public class LoginUseCaseTest {
    private final static ForTestAdminUserAggregateRepository adminUserRepos = ForTestAdminUserAggregateRepository.getInstance();

    @BeforeEach
    void setUp(TestInfo testInfo) {
//        System.out.println(String.format("test started: %s", testInfo.getDisplayName()));
    }

    @Test
    public void 基本系列_システム管理コンソールへログインする() throws Exception {
        LoginUseCase useCase = new LoginUseCase(adminUserRepos);
        Result result = useCase.execute(
                ForTestAdminUserAggregateRepository.テスト用の有効な管理者メールアドレス,
                ForTestAdminUserAggregateRepository.テスト用Password
        );
        assertTrue(result.isSuccess());
        assertThat(result.adminUserAggregate().id()).isGreaterThan(0);
    }

    @Test
    public void 代替系列_入力されたメールアドレスの一致する有効な管理ユーザアカウントが無い() throws Exception {
        LoginUseCase useCase = new LoginUseCase(adminUserRepos);
        Result result = useCase.execute(
            ForTestAdminUserAggregateRepository.システムログイン時例外用_使用されていないメールアドレス
            , ForTestAdminUserAggregateRepository.テスト用Password
        );
        RuntimeException exception = result.exception();

        assertFalse(result.isSuccess());
        assertThat(result.eMessage()).isEqualTo(LoginUseCase.E_MSG_NOT_EXISTS);
        assertNotNull(exception);
        assertEquals(NotExistException.class, exception.getClass());
    }

    @Test
    public void 代替系列_入力されたパスワードが対象管理ユーザアカウントの管理ユーザパスワードと一致しない() throws Exception {
        LoginUseCase useCase = new LoginUseCase(adminUserRepos);
        Result result = useCase.execute(
                ForTestAdminUserAggregateRepository.テスト用の有効な管理者メールアドレス
                , ForTestAdminUserAggregateRepository.テスト用の誤ったPassword
        );
        RuntimeException exception = result.exception();

        assertFalse(result.isSuccess());
        assertThat(result.eMessage()).isEqualTo(LoginUseCase.E_MSG_PASSWORD_IS_NOT_MATCH);
        assertNotNull(exception);
        assertEquals(PasswordIsNotMatchException.class, exception.getClass());
    }
}
